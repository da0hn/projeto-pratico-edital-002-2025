package br.com.ghonda.application.rest.payload;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ApiFailureResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -7251924292127497383L;

    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();

    String message;

    @Builder.Default
    List<String> failures = new ArrayList<>(0);

    int code;

    String status;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.timestamp)
            .append(this.failures)
            .append(this.code)
            .append(this.status)
            .toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        final ApiFailureResponse that = (ApiFailureResponse) o;

        return new EqualsBuilder()
            .append(this.code, that.code)
            .append(this.timestamp, that.timestamp)
            .append(this.failures, that.failures)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("timestamp", this.timestamp)
            .append("errors", this.failures)
            .append("code", this.code)
            .append("status", this.status)
            .toString();
    }

}
