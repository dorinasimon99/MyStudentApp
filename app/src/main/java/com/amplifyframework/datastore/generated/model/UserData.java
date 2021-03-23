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

/** This is an auto generated class representing the UserData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class UserData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField ROLE = field("role");
  public static final QueryField GRADE = field("grade");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String", isRequired = true) String role;
  private final @ModelField(targetType="Int") Integer grade;
  private final @ModelField(targetType="UserCourse") @HasMany(associatedWith = "user", type = UserCourse.class) List<UserCourse> courses = null;
  private final @ModelField(targetType="CardData") @HasMany(associatedWith = "owner", type = CardData.class) List<CardData> cards = null;
  private final @ModelField(targetType="QuizData") @HasMany(associatedWith = "owner", type = QuizData.class) List<QuizData> quizzes = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getRole() {
      return role;
  }
  
  public Integer getGrade() {
      return grade;
  }
  
  public List<UserCourse> getCourses() {
      return courses;
  }
  
  public List<CardData> getCards() {
      return cards;
  }
  
  public List<QuizData> getQuizzes() {
      return quizzes;
  }
  
  private UserData(String id, String name, String role, Integer grade) {
    this.id = id;
    this.name = name;
    this.role = role;
    this.grade = grade;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserData userData = (UserData) obj;
      return ObjectsCompat.equals(getId(), userData.getId()) &&
              ObjectsCompat.equals(getName(), userData.getName()) &&
              ObjectsCompat.equals(getRole(), userData.getRole()) &&
              ObjectsCompat.equals(getGrade(), userData.getGrade());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getRole())
      .append(getGrade())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("grade=" + String.valueOf(getGrade()))
      .append("}")
      .toString();
  }
  
  public static RoleStep builder() {
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
  public static UserData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UserData(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      role,
      grade);
  }
  public interface RoleStep {
    BuildStep role(String role);
  }
  

  public interface BuildStep {
    UserData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep grade(Integer grade);
  }
  

  public static class Builder implements RoleStep, BuildStep {
    private String id;
    private String role;
    private String name;
    private Integer grade;
    @Override
     public UserData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserData(
          id,
          name,
          role,
          grade);
    }
    
    @Override
     public BuildStep role(String role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep grade(Integer grade) {
        this.grade = grade;
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
    private CopyOfBuilder(String id, String name, String role, Integer grade) {
      super.id(id);
      super.role(role)
        .name(name)
        .grade(grade);
    }
    
    @Override
     public CopyOfBuilder role(String role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder grade(Integer grade) {
      return (CopyOfBuilder) super.grade(grade);
    }
  }
  
}
