package nvb.dev.springadvancedproject;

import nvb.dev.springadvancedproject.dto.*;
import nvb.dev.springadvancedproject.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MotherObject {

    public static final Long ANY_ID = 123L;
    public static final String ANY_UUID_ID = "2fe0d30d-4788-4b15-86f9-368c79123bb6";
    public static final String ANY_FIRST_NAME = "dummy";
    public static final String ANY_LAST_NAME = "dummy";
    public static final String ANY_FULL_NAME = "dummy dummy";
    public static final String ANY_USERNAME = "dummy";
    public static final String ANY_PASSWORD = "dummy";
    public static final Integer ANY_AGE = 20;
    public static final String ANY_EMAIL = "dummy@dummy.com";
    public static final String ANY_COUNTRY = "dummy";
    public static final String ANY_CITY = "dummy";

    public static final String ANY_STRING = "anyString";

    public static AuthorEntity anyValidAuthorEntity() {
        return AuthorEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_FIRST_NAME)
                .lastName(ANY_LAST_NAME)
                .age(ANY_AGE)
                .books(new ArrayList<>())
                .build();
    }

    public static AuthorEntity anyInvalidAuthorEntity() {
        return AuthorEntity.builder()
                .id(null)
                .firstName(null)
                .lastName(null)
                .age(null)
                .books(null)
                .build();
    }

    public static AuthorDto anyValidAuthorDto() {
        return AuthorDto.builder()
                .id(ANY_ID)
                .firstName(ANY_FIRST_NAME)
                .lastName(ANY_LAST_NAME)
                .age(ANY_AGE)
                .books(new ArrayList<>())
                .build();
    }

    public static AuthorDto anyInvalidAuthorDto() {
        return AuthorDto.builder()
                .id(null)
                .firstName(null)
                .lastName(null)
                .age(null)
                .books(null)
                .build();
    }

    public static BookEntity anyValidBookEntity() {
        return BookEntity.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .isbn(ANY_STRING)
                .pages(50)
                .rating(5)
                .genres(List.of("Fiction", "Adult", "Romance"))
                .publishedYear(LocalDate.now())
                .author(new AuthorEntity())
                .member(new MemberEntity())
                .loans(new ArrayList<>())
                .build();
    }

    public static BookEntity anyInvalidBookEntity() {
        return BookEntity.builder()
                .id(null)
                .title(null)
                .isbn(null)
                .pages(0)
                .rating(0)
                .genres(null)
                .publishedYear(null)
                .author(null)
                .member(null)
                .loans(null)
                .build();
    }

    public static BookDto anyValidBookDto() {
        return BookDto.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .isbn(ANY_STRING)
                .pages(50)
                .rating(5)
                .genres(List.of("Fiction", "Adult", "Romance"))
                .publishedYear(LocalDate.now())
                .author(new AuthorEntity())
                .member(new MemberEntity())
                .loans(new ArrayList<>())
                .build();
    }

    public static BookDto anyInvalidBookDto() {
        return BookDto.builder()
                .id(null)
                .title(null)
                .isbn(null)
                .pages(0)
                .rating(0)
                .genres(null)
                .publishedYear(null)
                .author(null)
                .member(null)
                .loans(null)
                .build();
    }

    public static LoanEntity anyValidLoanEntity() {
        return LoanEntity.builder()
                .id(ANY_ID)
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .member(new MemberEntity())
                .book(new BookEntity())
                .build();
    }

    public static LoanEntity anyInvalidLoanEntity() {
        return LoanEntity.builder()
                .id(null)
                .loanDate(null)
                .dueDate(null)
                .returnDate(null)
                .member(null)
                .book(null)
                .build();
    }

    public static LoanDto anyValidLoanDto() {
        return LoanDto.builder()
                .id(ANY_ID)
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .member(new MemberEntity())
                .book(new BookEntity())
                .build();
    }

    public static LoanDto anyInvalidLoanDto() {
        return LoanDto.builder()
                .id(null)
                .loanDate(null)
                .dueDate(null)
                .returnDate(null)
                .member(null)
                .book(null)
                .build();
    }

    public static MemberEntity anyValidMemberEntity() {
        return MemberEntity.builder()
                .id(ANY_ID)
                .fullName(ANY_FULL_NAME)
                .email(ANY_EMAIL)
                .address(new Address(ANY_COUNTRY, ANY_CITY))
                .type("Gold")
                .borrowedBooks(new ArrayList<>())
                .build();
    }

    public static MemberEntity anyInvalidMemberEntity() {
        return MemberEntity.builder()
                .id(null)
                .fullName(null)
                .email(null)
                .address(null)
                .type(null)
                .borrowedBooks(null)
                .build();
    }

    public static MemberDto anyValidMemberDto() {
        return MemberDto.builder()
                .id(ANY_ID)
                .fullName(ANY_FULL_NAME)
                .email(ANY_EMAIL)
                .address(new Address(ANY_COUNTRY, ANY_CITY))
                .type("Gold")
                .borrowedBooks(new ArrayList<>())
                .build();
    }

    public static MemberDto anyInvalidMemberDto() {
        return MemberDto.builder()
                .id(null)
                .fullName(null)
                .email(null)
                .address(null)
                .type(null)
                .borrowedBooks(null)
                .build();
    }

    public static UserEntity anyValidUserEntity() {
        return UserEntity.builder()
                .id(ANY_UUID_ID)
                .firstName(ANY_FIRST_NAME)
                .lastName(ANY_LAST_NAME)
                .username(ANY_USERNAME)
                .email(ANY_EMAIL)
                .password(ANY_PASSWORD)
                .userRole(UserRole.USER)
                .build();
    }

    public static UserEntity anyInvalidUserEntity() {
        return UserEntity.builder()
                .id(null)
                .firstName(null)
                .lastName(null)
                .username(null)
                .email(null)
                .password(null)
                .userRole(null)
                .build();
    }

    public static UserDto anyValidUserDto() {
        return UserDto.builder()
                .id(ANY_UUID_ID)
                .firstName(ANY_FIRST_NAME)
                .lastName(ANY_LAST_NAME)
                .username(ANY_USERNAME)
                .email(ANY_EMAIL)
                .password(ANY_PASSWORD)
                .userRole(UserRole.USER)
                .build();
    }

    public static UserDto anyInvalidUserDto() {
        return UserDto.builder()
                .id(null)
                .firstName(null)
                .lastName(null)
                .username(null)
                .email(null)
                .password(null)
                .userRole(null)
                .build();
    }

}