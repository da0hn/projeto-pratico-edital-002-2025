package br.com.ghonda.application.rest.payload;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;

@Value
@Builder
public class ApiCollectionResponse<T> {

  Boolean success;

  Collection<T> data;

  public static <T> ApiCollectionResponse<T> of(final Collection<T> data) {
    return new ApiCollectionResponse<>(true, data);
  }

  public static <T> ApiCollectionResponse<T> empty() {
    return new ApiCollectionResponse<>(true, new ArrayList<>());
  }

}
