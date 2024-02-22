package org.example.models.keyspaces.codec;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class TimeStampAsStringCodec implements TypeCodec<String> {

    private static final String CASSANDRA_TIMESTAMP_FORMAT = "yyyy-MM-dd";
    private static final String PST = "America/Los_Angeles";

    @NonNull
    @Override
    public GenericType<String> getJavaType() {
        return GenericType.STRING;
    }

    @NonNull
    @Override
    public DataType getCqlType() {
        return DataTypes.TIMESTAMP;
    }

    @Nullable
    @Override
    public ByteBuffer encode(@Nullable String value, @NonNull ProtocolVersion protocolVersion) {
        if (value == null) {
            return null;
        } else {
            DateTimeFormatter DTF = new DateTimeFormatterBuilder()
                    .appendPattern(CASSANDRA_TIMESTAMP_FORMAT)
                    .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
                    .toFormatter()
                    .withZone(ZoneId.of(PST));

            Instant timestampStringValue = DTF.parse(value, Instant::from);
            return TypeCodecs.TIMESTAMP.encode(timestampStringValue, protocolVersion);
        }
    }

    @Nullable
    @Override
    public String decode(@Nullable ByteBuffer byteBuffer, @NonNull ProtocolVersion protocolVersion) {
        Instant timestampValue = TypeCodecs.TIMESTAMP.decode(byteBuffer, protocolVersion);
        return timestampValue.toString();
    }

    @NonNull
    @Override
    public String format(@Nullable String str) {
        Instant timestampValue = Instant.parse(str);
        return TypeCodecs.TIMESTAMP.format(timestampValue);
    }

    @Nullable
    @Override
    public String parse(@Nullable String s) {
        Instant timestampValue = TypeCodecs.TIMESTAMP.parse(s);
        return timestampValue == null ? null : timestampValue.toString();
    }
}
