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

/** This is an auto generated class representing the QuizData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "QuizData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "users" }, operations = { ModelOperation.READ })
})
public final class QuizData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField OWNER_ID = field("ownerID");
  public static final QueryField QUESTIONS = field("questions");
  public static final QueryField ANSWERS = field("answers");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String ownerID;
  private final @ModelField(targetType="String") List<String> questions;
  private final @ModelField(targetType="String") List<String> answers;
  public String getId() {
      return id;
  }
  
  public String getOwnerId() {
      return ownerID;
  }
  
  public List<String> getQuestions() {
      return questions;
  }
  
  public List<String> getAnswers() {
      return answers;
  }
  
  private QuizData(String id, String ownerID, List<String> questions, List<String> answers) {
    this.id = id;
    this.ownerID = ownerID;
    this.questions = questions;
    this.answers = answers;
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
              ObjectsCompat.equals(getOwnerId(), quizData.getOwnerId()) &&
              ObjectsCompat.equals(getQuestions(), quizData.getQuestions()) &&
              ObjectsCompat.equals(getAnswers(), quizData.getAnswers());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOwnerId())
      .append(getQuestions())
      .append(getAnswers())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("QuizData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("ownerID=" + String.valueOf(getOwnerId()) + ", ")
      .append("questions=" + String.valueOf(getQuestions()) + ", ")
      .append("answers=" + String.valueOf(getAnswers()))
      .append("}")
      .toString();
  }
  
  public static OwnerIdStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      ownerID,
      questions,
      answers);
  }
  public interface OwnerIdStep {
    BuildStep ownerId(String ownerId);
  }
  

  public interface BuildStep {
    QuizData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep questions(List<String> questions);
    BuildStep answers(List<String> answers);
  }
  

  public static class Builder implements OwnerIdStep, BuildStep {
    private String id;
    private String ownerID;
    private List<String> questions;
    private List<String> answers;
    @Override
     public QuizData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new QuizData(
          id,
          ownerID,
          questions,
          answers);
    }
    
    @Override
     public BuildStep ownerId(String ownerId) {
        Objects.requireNonNull(ownerId);
        this.ownerID = ownerId;
        return this;
    }
    
    @Override
     public BuildStep questions(List<String> questions) {
        this.questions = questions;
        return this;
    }
    
    @Override
     public BuildStep answers(List<String> answers) {
        this.answers = answers;
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
    private CopyOfBuilder(String id, String ownerId, List<String> questions, List<String> answers) {
      super.id(id);
      super.ownerId(ownerId)
        .questions(questions)
        .answers(answers);
    }
    
    @Override
     public CopyOfBuilder ownerId(String ownerId) {
      return (CopyOfBuilder) super.ownerId(ownerId);
    }
    
    @Override
     public CopyOfBuilder questions(List<String> questions) {
      return (CopyOfBuilder) super.questions(questions);
    }
    
    @Override
     public CopyOfBuilder answers(List<String> answers) {
      return (CopyOfBuilder) super.answers(answers);
    }
  }
  
}
