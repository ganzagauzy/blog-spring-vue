# blog-spring-vue

A blog is a type of website or online platform where individuals or groups regularly publish written content, often in a conversational or informal style. The term "blog" is a short form of "weblog." Blogs typically consist of discrete entries or posts displayed in reverse chronological order, with the most recent post appearing first.

## Project Requirements

### Requirements
The requirements of this project are authentication, blog or post managment (edit,read,update,delete) and also comment interactive where clients can give comments to a certain blog or post.

### Outcomes
-Authentication: unauthorised user is not allowed to access some pages only if he/she is authenticated.
-Blog managment: the admin has full access on blogs, he/she can create,edit,delete,read actions on blogs.
-Comments: clients will be able to send their reviews or replies through comments on a certain post.
-Emailing: Once a client send a comment the admin will be notified that someone has posted a comment on a certain blog and the client will be notified that his/her comment has been received.

## Project Plan

### Technologies

1. Java spring boot

```bash
Backednd withJava springboot
```

2. Nuxt js 3

```bash
Frontend with Nuxt js 3
```
3. Tailwind Css

```bash
Styling with Tailwind css
```

### Features

1. Authentication
2. Emailing
3. Crud

## Source Code

1.Backend
- have jdk installed
```bash
https://github.com/ganzagauzy/blog-spring-vue/tree/main/backend/blog
```
2.Frontend
-have node js installed
- cd blog
- pnpm install
- pnpm dev
  
```bash
https://github.com/ganzagauzy/blog-spring-vue/tree/main/frontend/blog
```

## Database Schema
1.User
```bash
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;
}
```
2.Blog or Post

```bash
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ElementCollection
    private List<String> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments;

    @CreationTimestamp
   private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;
}
```
3.Comment

```bash
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String name;
    private String email;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;
}
```

## User documentation

### Navigations
-Home: it redirects you to the landing page of the project.
-Blogs: it redirects you to the the blog page where clients can view all blogs.
-Contact: it redirects you to contact page where you can get owner's info.
-Admin: it redirects you to the admin page where the admin manages the blogs or posts.
-Register: it redirects you to the register page where you can register your self and become an admin.
-Login: it redirects you to login where you put your credentials and be logged in to access admin side.
-Logout: it logs the user out and redirect to landing page

## Technical Documentation

### Architecture
-MVC: Model View Controller

### Frameworks

-Java Springboot.
-Nuxt js
-Tailwind css









