package hive.tutorial.customization.inputs;

import hmr.tutorial.customization.models.Asin;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.SettableStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class AsinHiveSerDe extends AbstractSerDe {

    private SettableStructObjectInspector objectInspector;
    private List<TypeInfo> columnTypes;
    private List<String> columnNames;

    private static final Log logger = LogFactory.getLog(AsinHiveSerDe.class);

    /**
     * Get table meta info. Prepare
     *
     * @param configuration
     * @param properties
     * @throws SerDeException
     */
    @Override
    public void initialize(Configuration configuration, Properties properties) throws SerDeException {
        logger.info(">>> table properties:" + properties);

        String columnNamesStr = properties.getProperty(serdeConstants.LIST_COLUMNS);
        columnNames = Arrays.asList(columnNamesStr.split(","));
        logger.info(">>> column names: " + columnNamesStr);

        String columnTypesStr = properties.getProperty(serdeConstants.LIST_COLUMN_TYPES);
        columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(columnTypesStr);
        logger.info(">>> column types: " + columnTypesStr);

        List<ObjectInspector> objectInspectors = getObjectInspectors();
        objectInspector = ObjectInspectorFactory.getStandardStructObjectInspector(columnNames, objectInspectors);
    }

    private List<ObjectInspector> getObjectInspectors() {
        List<ObjectInspector> ret = new ArrayList<ObjectInspector>();
        for (TypeInfo typeInfo : columnTypes) {
            ObjectInspector objectInspector = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(typeInfo);
            ret.add(objectInspector);
        }
        return ret;
    }

    @Override
    public Class<? extends Writable> getSerializedClass() {
        return AsinWritable.class;
    }

    @Override
    public Writable serialize(Object o, ObjectInspector objectInspector) throws SerDeException {
        throw new SerDeException("Read only!");
    }

    @Override
    public SerDeStats getSerDeStats() {
        return null;
    }

    /**
     * Deserialize value object. In this implementation, it requires hive table column name is the Asin models attribute name.
     *
     * @param writable
     * @return
     * @throws SerDeException
     */
    @Override
    public Object deserialize(Writable writable) throws SerDeException {
        if (!(writable instanceof AsinWritable)) {
            throw new SerDeException(String.format("expected AsinWritable but got '%s'", writable.getClass()));
        }
        logger.info(">>> writable type is: " + writable.getClass().getSimpleName() + ", content is: " + writable.toString());

        AsinWritable asinWritable = (AsinWritable) writable;
        Asin asin = asinWritable.getAsin();
        Object dataObject = objectInspector.create();

        List<? extends StructField> structFieldRefs = objectInspector.getAllStructFieldRefs();


        for (StructField field : structFieldRefs) {
            logger.info(">>> field type: " + field.getClass().getSimpleName() + ", field: " + field);
            try {
                objectInspector.setStructFieldData(dataObject, field, asin.getData(field.getFieldName()));
            } catch (Exception e) {
                logger.error(e);
                throw new SerDeException(e);
            }
        }

        return dataObject;
    }

    @Override
    public ObjectInspector getObjectInspector() throws SerDeException {
        return objectInspector;
    }
}
