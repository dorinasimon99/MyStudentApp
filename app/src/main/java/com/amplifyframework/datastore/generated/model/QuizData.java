package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

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

/** This is an auto generated class representing the QuizData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "QuizData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byUser", fields = {"ownerID"})
@Index(name = "byCourse", fields = {"courseID"})
public final class QuizData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField OWNER = field("ownerID");
  public static final QueryField COURSE = field("courseID");
  public static final QueryField QUIZ_QUESTIONS = field("quizQuestions");
  public static final QueryField QUIZ_ANSWERS = field("quizAnswers");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="UserData") @BelongsTo(targetName = "ownerID", type = UserData.class) UserData owner;
  private final @ModelField(targetType="CourseData") @BelongsTo(targetName = "courseID", type = CourseData.class) CourseData course;
  private final @ModelField(targetType="String") List<String> quizQuestions;
  private final @ModelField(targetType="String") List<String> quizAnswers;
  public String getId() {
      return id;
  }
  
  public UserData getOwner() {
      return owner;
  }
  
  public CourseData getCourse() {
      return course;
  }
  
  public List<String> getQuizQuestions() {
      return quizQuestions;
  }
  
  public List<String> getQuizAnswers() {
      return quizAnswers;
  }
  
  private QuizData(String id, UserData owner, CourseData course, List<String> quizQuestions, List<String> quizAnswers) {
    this.id = id;
    this.owner = owner;
    this.course = course;
    this.quizQuestions = quizQuestions;
    this.quizAnswers = quizAnswers;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      QuizData quizData = (QuizData) obj;
      return ObjectsCompat.equals(getId(), quizData.getId()) &&
              ObjectsCompat.equals(getOwner(), quizData.getOwner()) &&
              ObjectsCompat.equals(getCourse(), quizData.getCourse()) &&
              ObjectsCompat.equals(getQuizQuestions(), quizData.getQuizQuestions()) &&
              ObjectsCompat.equals(getQuizAnswers(), quizData.getQuizAnswers());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOwner())
      .append(getCourse())
      .append(getQuizQuestions())
      .append(getQuizAnswers())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("QuizData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("course=" + String.valueOf(getCourse()) + ", ")
      .append("quizQuestions=" + String.valueOf(getQuizQuestions()) + ", ")
      .append("quizAnswers=" + String.valueOf(getQuizAnswers()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static QuizData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new QuizData(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      owner,
      course,
      quizQuestions,
      quizAnswers);
  }
  public interface BuildStep {
    QuizData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep owner(UserData owner);
    BuildStep course(CourseData course);
    BuildStep quizQuestions(List<String> quizQuestions);
    BuildStep quizAnswers(List<String> quizAnswers);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private UserData owner;
    private CourseData course;
    private List<String> quizQuestions;
    private List<String> quizAnswers;
    @Override
     public QuizData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new QuizData(
          id,
          owner,
          course,
          quizQuestions,
          quizAnswers);
    }
    
    @Override
     public BuildStep owner(UserData owner) {
        this.owner = owner;
        return this;
    }
    
    @Override
     public BuildStep course(CourseData course) {
        this.course = course;
        return this;
    }
    
    @Override
     public BuildStep quizQuestions(List<String> quizQuestions) {
        this.quizQuestions = quizQuestions;
        return this;
    }
    
    @Override
     public BuildStep quizAnswers(List<String> quizAnswers) {
        this.quizAnswers = quizAnswers;
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
    private CopyOfBuilder(String id, UserData owner, CourseData course, List<String> quizQuestions, List<String> quizAnswers) {
      super.id(id);
      super.owner(owner)
        .course(course)
        .quizQuestions(quizQuestions)
        .quizAnswers(quizAnswers);
    }
    
    @Override
     public CopyOfBuilder owner(UserData owner) {
      return (CopyOfBuilder) super.owner(owner);
    }
    
    @Override
     public CopyOfBuilder course(CourseData course) {
      return (CopyOfBuilder) super.course(course);
    }
    
    @Override
     public CopyOfBuilder quizQuestions(List<String> quizQuestions) {
      return (CopyOfBuilder) super.quizQuestions(quizQuestions);
    }
    
    @Override
     public CopyOfBuilder quizAnswers(List<String> quizAnswers) {
      return (CopyOfBuilder) super.quizAnswers(quizAnswers);
    }
  }
  
}
