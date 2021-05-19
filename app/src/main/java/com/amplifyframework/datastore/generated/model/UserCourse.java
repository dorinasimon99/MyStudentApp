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

/** This is an auto generated class representing the UserCourse type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserCourses", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.DELETE })
})
public final class UserCourse implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField USER = field("userCourseUserId");
  public static final QueryField COURSE = field("userCourseCourseId");
  public static final QueryField RATING = field("rating");
  public static final QueryField RATING_NUM = field("ratingNum");
  public static final QueryField SEMESTER = field("semester");
  public static final QueryField GRADE = field("grade");
  public static final QueryField LESSONS = field("lessons");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="UserData", isRequired = true) @BelongsTo(targetName = "userCourseUserId", type = UserData.class) UserData user;
  private final @ModelField(targetType="CourseData", isRequired = true) @BelongsTo(targetName = "userCourseCourseId", type = CourseData.class) CourseData course;
  private final @ModelField(targetType="String") String rating;
  private final @ModelField(targetType="Int") Integer ratingNum;
  private final @ModelField(targetType="Int") Integer semester;
  private final @ModelField(targetType="Int") Integer grade;
  private final @ModelField(targetType="String") List<String> lessons;
  public String getId() {
      return id;
  }
  
  public UserData getUser() {
      return user;
  }
  
  public CourseData getCourse() {
      return course;
  }
  
  public String getRating() {
      return rating;
  }
  
  public Integer getRatingNum() {
      return ratingNum;
  }
  
  public Integer getSemester() {
      return semester;
  }
  
  public Integer getGrade() {
      return grade;
  }
  
  public List<String> getLessons() {
      return lessons;
  }
  
  private UserCourse(String id, UserData user, CourseData course, String rating, Integer ratingNum, Integer semester, Integer grade, List<String> lessons) {
    this.id = id;
    this.user = user;
    this.course = course;
    this.rating = rating;
    this.ratingNum = ratingNum;
    this.semester = semester;
    this.grade = grade;
    this.lessons = lessons;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserCourse userCourse = (UserCourse) obj;
      return ObjectsCompat.equals(getId(), userCourse.getId()) &&
              ObjectsCompat.equals(getUser(), userCourse.getUser()) &&
              ObjectsCompat.equals(getCourse(), userCourse.getCourse()) &&
              ObjectsCompat.equals(getRating(), userCourse.getRating()) &&
              ObjectsCompat.equals(getRatingNum(), userCourse.getRatingNum()) &&
              ObjectsCompat.equals(getSemester(), userCourse.getSemester()) &&
              ObjectsCompat.equals(getGrade(), userCourse.getGrade()) &&
              ObjectsCompat.equals(getLessons(), userCourse.getLessons());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getCourse())
      .append(getRating())
      .append(getRatingNum())
      .append(getSemester())
      .append(getGrade())
      .append(getLessons())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserCourse {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("course=" + String.valueOf(getCourse()) + ", ")
      .append("rating=" + String.valueOf(getRating()) + ", ")
      .append("ratingNum=" + String.valueOf(getRatingNum()) + ", ")
      .append("semester=" + String.valueOf(getSemester()) + ", ")
      .append("grade=" + String.valueOf(getGrade()) + ", ")
      .append("lessons=" + String.valueOf(getLessons()))
      .append("}")
      .toString();
  }
  
  public static UserStep builder() {
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
  public static UserCourse justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UserCourse(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      course,
      rating,
      ratingNum,
      semester,
      grade,
      lessons);
  }
  public interface UserStep {
    CourseStep user(UserData user);
  }
  

  public interface CourseStep {
    BuildStep course(CourseData course);
  }
  

  public interface BuildStep {
    UserCourse build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep rating(String rating);
    BuildStep ratingNum(Integer ratingNum);
    BuildStep semester(Integer semester);
    BuildStep grade(Integer grade);
    BuildStep lessons(List<String> lessons);
  }
  

  public static class Builder implements UserStep, CourseStep, BuildStep {
    private String id;
    private UserData user;
    private CourseData course;
    private String rating;
    private Integer ratingNum;
    private Integer semester;
    private Integer grade;
    private List<String> lessons;
    @Override
     public UserCourse build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserCourse(
          id,
          user,
          course,
          rating,
          ratingNum,
          semester,
          grade,
          lessons);
    }
    
    @Override
     public CourseStep user(UserData user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep course(CourseData course) {
        Objects.requireNonNull(course);
        this.course = course;
        return this;
    }
    
    @Override
     public BuildStep rating(String rating) {
        this.rating = rating;
        return this;
    }
    
    @Override
     public BuildStep ratingNum(Integer ratingNum) {
        this.ratingNum = ratingNum;
        return this;
    }
    
    @Override
     public BuildStep semester(Integer semester) {
        this.semester = semester;
        return this;
    }
    
    @Override
     public BuildStep grade(Integer grade) {
        this.grade = grade;
        return this;
    }
    
    @Override
     public BuildStep lessons(List<String> lessons) {
        this.lessons = lessons;
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
    private CopyOfBuilder(String id, UserData user, CourseData course, String rating, Integer ratingNum, Integer semester, Integer grade, List<String> lessons) {
      super.id(id);
      super.user(user)
        .course(course)
        .rating(rating)
        .ratingNum(ratingNum)
        .semester(semester)
        .grade(grade)
        .lessons(lessons);
    }
    
    @Override
     public CopyOfBuilder user(UserData user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder course(CourseData course) {
      return (CopyOfBuilder) super.course(course);
    }
    
    @Override
     public CopyOfBuilder rating(String rating) {
      return (CopyOfBuilder) super.rating(rating);
    }
    
    @Override
     public CopyOfBuilder ratingNum(Integer ratingNum) {
      return (CopyOfBuilder) super.ratingNum(ratingNum);
    }
    
    @Override
     public CopyOfBuilder semester(Integer semester) {
      return (CopyOfBuilder) super.semester(semester);
    }
    
    @Override
     public CopyOfBuilder grade(Integer grade) {
      return (CopyOfBuilder) super.grade(grade);
    }
    
    @Override
     public CopyOfBuilder lessons(List<String> lessons) {
      return (CopyOfBuilder) super.lessons(lessons);
    }
  }
  
}
