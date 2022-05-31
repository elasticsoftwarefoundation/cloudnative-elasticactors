/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.21.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.elasticsoftware.elasticactors.cloudnative.operator.models;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * V1ActorSystemSpec
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-05-31T10:50:42.749Z[Etc/UTC]")
public class V1ActorSystemSpec {
  public static final String SERIALIZED_NAME_SHARDS = "shards";
  @SerializedName(SERIALIZED_NAME_SHARDS)
  private Integer shards;


  public V1ActorSystemSpec shards(Integer shards) {
    
    this.shards = shards;
    return this;
  }

   /**
   * Get shards
   * @return shards
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getShards() {
    return shards;
  }


  public void setShards(Integer shards) {
    this.shards = shards;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ActorSystemSpec v1ActorSystemSpec = (V1ActorSystemSpec) o;
    return Objects.equals(this.shards, v1ActorSystemSpec.shards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shards);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ActorSystemSpec {\n");
    sb.append("    shards: ").append(toIndentedString(shards)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

