package hive.tutorial.customization.inputs;

import org.apache.hadoop.hive.metastore.HiveMetaHook;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.ql.metadata.DefaultStorageHandler;
import org.apache.hadoop.hive.serde2.SerDe;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.OutputFormat;


/**
 * Storage handler will provide input/output format/serde to hive. Thus hive knows how to deserialize data.
 */
public class AsinHiveStorageHandler extends DefaultStorageHandler
        implements HiveMetaHook {

    @Override
    public Class<? extends InputFormat> getInputFormatClass() {
        return AsinHiveInputFormat.class;
    }

    @Override
    public Class<? extends OutputFormat> getOutputFormatClass() {
        return AsinHiveOutputFormat.class;
    }

    @Override
    public Class<? extends SerDe> getSerDeClass() {
        return AsinHiveSerDe.class;
    }

    @Override
    public HiveMetaHook getMetaHook() {
        return this;
    }

    @Override
    public void preCreateTable(Table table) throws MetaException {
        // Not implemented.
    }

    @Override
    public void rollbackCreateTable(Table table) throws MetaException {
        // Not implemented.
    }

    @Override
    public void commitCreateTable(Table table) throws MetaException {
        // Not implemented.
    }

    @Override
    public void preDropTable(Table table) throws MetaException {
        // Not implemented.
    }

    @Override
    public void rollbackDropTable(Table table) throws MetaException {
        // Not implemented.
    }

    @Override
    public void commitDropTable(Table table, boolean b) throws MetaException {
        // Not implemented.
    }
}
