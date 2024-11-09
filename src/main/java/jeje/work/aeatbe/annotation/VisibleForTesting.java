 package jeje.work.aeatbe.annotation;

 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;

 /**
  * 이 어노테이션은 해당 메서드가 테스트 용도로만 사용된다는 것을 나타내기 위한 마커 애너테이션입니다.
  * 테스트에 사용되는 메서드와 실제 프로덕션에 사용되는 메서드를 구분하는 데 사용됩니다.
  */
 @Retention(RetentionPolicy.SOURCE)
 @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
 public @interface VisibleForTesting {
 } 삭제 예정
