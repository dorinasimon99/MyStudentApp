package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the CourseData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CourseData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.DELETE })
})
public final class CourseData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField COURSE_CODE = field("courseCode");
  public static final QueryField NAME = field("name");
  public static final QueryField CREDITS = field("credits");
  public static final QueryField TIME = field( "time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String courseCode;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="UserCourse") @HasMany(associatedWith = "course", type = UserCourse.class) List<UserCourse> users = null;
  private final @ModelField(targetType="Int", isRequired = true) Integer credits;
  private final @ModelField(targetType="CardData") @HasMany(associatedWith = "course", type = CardData.class) List<CardData> cards = null;
  private final @ModelField(targetType="QuizData") @HasMany(associatedWith = "course", type = QuizData.class) List<QuizData> quizzes = null;
  private final @ModelField(targetType="String", isRequired = true) String time;
  public String getId() {
      return id;
  }
  
  public String getCourseCode() {
      return courseCode;
  }
  
  public String getName() {
      return name;
  }
  
  public List<UserCourse> getUsers() {
      return users;
  }
  
  public Integer getCredits() {
      return credits;
  }
  
  public List<CardData> getCards() {
      return cards;
  }
  
  public List<QuizData> getQuizzes() {
      return quizzes;
  }
  
  public String getTime() {
      return time;
  }
  
  private CourseData(String id, String courseCode, String name, Integer credits, String time) {
    this.id = id;
    this.courseCode = courseCode;
    this.name = name;
    this.credits = credits;
    this.time = time;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CourseData courseData = (CourseData) obj;
      return ObjectsCompat.equals(getId(), courseData.getId()) &&
              ObjectsCompat.equals(getCourseCode(), courseData.getCourseCode()) &&
              ObjectsCompat.equals(getName(), courseData.getName()) &&
              ObjectsCompat.equals(getCredits(), courseData.getCredits()) &&
              ObjectsCompat.equals(getTime(), courseData.getTime());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCourseCode())
      .append(getName())
      .append(getCredits())
      .append(getTime())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CourseData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("courseCode=" + String.valueOf(getCourseCode()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("credits=" + String.valueOf(getCredits()) + ", ")
      .append("time=" + String.valueOf(getTime()))
      .append("}")
      .toString();
  }
  
  public static CourseCodeStep builder() {
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
  public static CourseData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new CourseData(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      courseCode,
      name,
      credits,
      time);
  }
  public interface CourseCodeStep {
    NameStep courseCode(String courseCode);
  }
  

  public interface NameStep {
    CreditsStep name(String name);
  }
  

  public interface CreditsStep {
    TimeStep credits(Integer credits);
  }
  

  public interface TimeStep {
    BuildStep time(String time);
  }
  

  public interface BuildStep {
    CourseData build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements CourseCodeStep, NameStep, CreditsStep, TimeStep, BuildStep {
    private String id;
    private String courseCode;
    private String name;
    private Integer credits;
    private String time;
    @Override
     public CourseData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CourseData(
          id,
          courseCode,
          name,
          credits,
          time);
    }
    
    @Override
     public NameStep courseCode(String courseCode) {
        Objects.requireNonNull(courseCode);
        this.courseCode = courseCode;
        return this;
    }
    
    @Override
     public CreditsStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public TimeStep credits(Integer credits) {
        Objects.requireNonNull(credits);
        this.credits = credits;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        Objects.requireNonNull(time);
        this.time = time;
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
    private CopyOfBuilder(String id, String courseCode, String name, Integer credits, String time) {
      super.id(id);
      super.courseCode(courseCode)
        .name(name)
        .credits(credits)
        .time(time);
    }
    
    @Override
     public CopyOfBuilder courseCode(String courseCode) {
      return (CopyOfBuilder) super.courseCode(courseCode);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder credits(Integer credits) {
      return (CopyOfBuilder) super.credits(credits);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
  }
  
}
