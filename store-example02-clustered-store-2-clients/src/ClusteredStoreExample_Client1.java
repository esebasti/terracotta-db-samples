import com.terracottatech.store.Dataset;
import com.terracottatech.store.DatasetWriterReader;
import com.terracottatech.store.StoreException;
import com.terracottatech.store.Type;
import com.terracottatech.store.configuration.DatasetConfiguration;
import com.terracottatech.store.definition.CellDefinition;
import com.terracottatech.store.definition.IntCellDefinition;
import com.terracottatech.store.definition.StringCellDefinition;
import com.terracottatech.store.manager.DatasetManager;

import java.io.IOException;
import java.net.URI;

import static com.terracottatech.store.UpdateOperation.write;

public class ClusteredStoreExample_Client1 {
  private static final String TERRACOTTA_URI_ENV = "TERRACOTTA_SERVER_URL";
  private static final String DEFAULT_TSA_PORT = "9410";
  private static final String STORE_NAME = "mySampleStore01";
  private static final IntCellDefinition FAVORITE_NUMBER_CELL = CellDefinition.defineInt("favoriteNumber");
  private static final StringCellDefinition NAME_CELL = CellDefinition.defineString("name");
  private static final IntCellDefinition AGE_CELL = CellDefinition.defineInt("age");
  private static final String SERVER_RESOURCE = "primary-server-resource";
  private static final String DEFAULT_SERVER_URI_STR = "terracotta://localhost:" + DEFAULT_TSA_PORT;
  private static final String SERVER_URI_STR = System.getenv(TERRACOTTA_URI_ENV) == null ? DEFAULT_SERVER_URI_STR : System.getenv(TERRACOTTA_URI_ENV);

  public static void main(String[] args) throws StoreException, IOException {
    try (DatasetManager datasetManager = DatasetManager.clustered(URI.create(SERVER_URI_STR)).build()) {
      // clean-up (delete) sample dataset from any previous run of this sample program
      datasetManager.destroyDataset(STORE_NAME);

      // create and use a dataset
      DatasetConfiguration offheapResource = datasetManager.datasetConfiguration().offheap(SERVER_RESOURCE).build();
      datasetManager.newDataset(STORE_NAME, Type.LONG, offheapResource);

      try (Dataset<Long> rawDataset = datasetManager.getDataset(STORE_NAME, Type.LONG)) {
        System.out.println("\n\nDataset created.\n");

        DatasetWriterReader<Long> myDataset = rawDataset.writerReader();

        myDataset.add(123L, NAME_CELL.newCell("George"), FAVORITE_NUMBER_CELL.newCell(42));
        myDataset.get(123L).ifPresent(rec ->
            System.out.println("Stored and retrieved a record with favorite number: " + rec.get(FAVORITE_NUMBER_CELL).get())
        );

        myDataset.update(123L, write(FAVORITE_NUMBER_CELL.name(), 7));
        myDataset.get(123L).ifPresent(rec ->
            System.out.println("Updated record with favorite number: " + rec.get(FAVORITE_NUMBER_CELL).get())
        );

        System.out.println("Please switch to ClusterStoreExample_Client2 now.");
        System.out.println("Press [ENTER] to continue...");
        System.in.read();

        myDataset.get(123L).ifPresent(rec ->
            System.out.println("Retrieved externally updated record with favorite number: " + rec.get(FAVORITE_NUMBER_CELL).get()
                + " and age: " + rec.get(AGE_CELL).orElseThrow(() -> new RuntimeException("Update from second client has not yet been applied!")))
        );

        if (myDataset.delete(123L)) {
          System.out.println("Deleted same record");
        }

        System.out.println("\nFinished.\n");
      }
    }
  }
}
