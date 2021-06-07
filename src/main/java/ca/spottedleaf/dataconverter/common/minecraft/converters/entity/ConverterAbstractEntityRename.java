package ca.spottedleaf.dataconverter.common.minecraft.converters.entity;

import ca.spottedleaf.dataconverter.common.converters.DataConverter;
import ca.spottedleaf.dataconverter.common.minecraft.converters.helpers.ConverterAbstractStringValueTypeRename;
import ca.spottedleaf.dataconverter.common.minecraft.datatypes.MCTypeRegistry;
import ca.spottedleaf.dataconverter.common.types.MapType;
import java.util.function.Function;

public final class ConverterAbstractEntityRename {

    private ConverterAbstractEntityRename() {}

    public static void register(final int version, final Function<String, String> renamer) {
        register(version, 0, renamer);
    }

    public static void register(final int version, final int subVersion, final Function<String, String> renamer) {
        MCTypeRegistry.ENTITY.addStructureConverter(new DataConverter<>(version) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final String id = data.getString("id");
                if (id == null) {
                    return null;
                }

                final String converted = renamer.apply(id);

                if (converted != null) {
                    data.setString("id", converted);
                }

                return null;
            }
        });
        ConverterAbstractStringValueTypeRename.register(version, subVersion, MCTypeRegistry.ENTITY_NAME, renamer);
    }

}
