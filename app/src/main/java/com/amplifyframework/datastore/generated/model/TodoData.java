package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TodoData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TodoData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE })
})
public final class TodoData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField DONE = field("done");
  public static final QueryField OWNER_ID = field("ownerID");
  public static final QueryField COURSE_ID = field("courseID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean done;
  private final @ModelField(targetType="ID", isRequired = true) String ownerID;
  private final @ModelField(targetType="ID", isRequired = true) String courseID;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Boolean getDone() {
      return done;
  }
  
  public String getOwnerId() {
      return ownerID;
  }
  
  public String getCourseId() {
      return courseID;
  }
  
  private TodoData(String id, String name, Boolean done, String ownerID, String courseID) {
    this.id = id;
    this.name = name;
    this.done = done;
    this.ownerID = ownerID;
    this.courseID = courseID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TodoData todoData = (TodoData) obj;
      return ObjectsCompat.equals(getId(), todoData.getId()) &&
              ObjectsCompat.equals(getName(), todoData.getName()) &&
              ObjectsCompat.equals(getDone(), todoData.getDone()) &&
              ObjectsCompat.equals(getOwnerId(), todoData.getOwnerId()) &&
              ObjectsCompat.equals(getCourseId(), todoData.getCourseId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDone())
      .append(getOwnerId())
      .append(getCourseId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TodoData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("done=" + String.valueOf(getDone()) + ", ")
      .append("ownerID=" + String.valueOf(getOwnerId()) + ", ")
      .append("courseID=" + String.valueOf(getCourseId()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static TodoData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new TodoData(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      done,
      ownerID,
      courseID);
  }
  public interface NameStep {
    DoneStep name(String name);
  }
  

  public interface DoneStep {
    OwnerIdStep done(Boolean done);
  }
  

  public interface OwnerIdStep {
    CourseIdStep ownerId(String ownerId);
  }
  

  public interface CourseIdStep {
    BuildStep courseId(String courseId);
  }
  

  public interface BuildStep {
    TodoData build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements NameStep, DoneStep, OwnerIdStep, CourseIdStep, BuildStep {
    private String id;
    private String name;
    private Boolean done;
    private String ownerID;
    private String courseID;
    @Override
     public TodoData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TodoData(
          id,
          name,
          done,
          ownerID,
          courseID);
    }
    
    @Override
     public DoneStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public OwnerIdStep done(Boolean done) {
        Objects.requireNonNull(done);
        this.done = done;
        return this;
    }
    
    @Override
     public CourseIdStep ownerId(String ownerId) {
        Objects.requireNonNull(ownerId);
        this.ownerID = ownerId;
        return this;
    }
    
    @Override
     public BuildStep courseId(String courseId) {
        Objects.requireNonNull(courseId);
        this.courseID = courseId;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Boolean done, String ownerId, String courseId) {
      super.id(id);
      super.name(name)
        .done(done)
        .ownerId(ownerId)
        .courseId(courseId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder done(Boolean done) {
      return (CopyOfBuilder) super.done(done);
    }
    
    @Override
     public CopyOfBuilder ownerId(String ownerId) {
      return (CopyOfBuilder) super.ownerId(ownerId);
    }
    
    @Override
     public CopyOfBuilder courseId(String courseId) {
      return (CopyOfBuilder) super.courseId(courseId);
    }
  }
  
}
