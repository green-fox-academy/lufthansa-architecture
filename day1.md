
# Day 1 - Architecture training

Architecture of a web application, and how to create web APIs.

## Materials & Resources

| Material                                                                                                |  Time |
| :------------------------------------------------------------------------------------------------------ | ----: |
| [Basic concepts of web applications and the HTTP protocol](https://www.youtube.com/watch?v=RsQ1tFLwldY) |  7:46 |
| [3 Tier Client Server Architecture](https://www.youtube.com/watch?v=jJYv-nfkMXk)                        |  4:26 |
| [What is MVC?](https://www.youtube.com/watch?v=wSalroADzbo)                                             |  5:50 |
| [What is Database & SQL?](https://www.youtube.com/watch?v=FR4QIeZaPeM)                                  |  6:19 |
| [REST API concepts and examples](https://www.youtube.com/watch?v=7YcW25PHnAA)                           |  8:52 |
| [JSON tutorial: What is JSON?](https://www.youtube.com/watch?v=40aKlrL-2V8)                             |  3:27 |
| [Getting Started With Postman](https://www.youtube.com/watch?v=q78_AJBGrVw)                             | 10:24 |

## Material Review

### Web architecture

- How does HTTP work?
  - What is the HTTP header?
  - What are the HTTP methods?
  - What's the HTTP Status code? What are the most well know ones?
  - How can you send a new HTTP request?
  - What are the parts of the URL and how to use them?
- What are the typical parts of a web application?
- What's a database?
  - How to connect to a MySQL database and read the contents of a table?
  - How to configure data source in a Spring Web Application?
- What's the classical 3-tier architecture?
  - What are the benefits of it?
- What's the MVC pattern?
- What's the Repository pattern?
- What's microservice architecture?

### REST

- What's a Web API?
- What's XML?
  - How to map Java classes to XML documents (using JAXB which is included in Java SE)?
  - What's XSD?
  - What's XPath?
    - How to search in XML files using XPath?
- What's JSON?
  - How to map Java classes to JSON objects?
- What's a REST API?
  - How to have uniform URLs?
  - What is the typical meaning of the HTTP methods (GET, POST, PUT, DELETE) in a REST API?
  - What will determine the representation of a resource?
  - How does the server know how to process the request?
  - What is dynamic discovery?

## Workshop

### Web architecture

Here is a [web app with no architecture](./workshop/terribleapp), everything is in a single file and let's try to identify the different layers and start separating them.

At the end you should have every SQL query in repositories, and every presentation logic in separate template files.

### XML

There are possibly infinite amount of ways handling XML in Java, we're going to use **JAXB** which is bundled with Java SE.

**Example XML:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<message>
    <title>Hello</title>
    <body>Hello from the XML</body>
    <attachments>
        <files type="image">hello.jpg</files>
        <files type="image">howdy.png</files>
        <files type="signature">sign.pgp</files>
    </attachments>
</message>
```

To parse the XML document you first must create a Java class, **note the `@XmlRootElement` annotation**:

```java
@XmlRootElement
class Message {
  public String title;
  public String body;
  public Attachment attachments;

  public static class Attachment {
    public List<String> files;
  }
}
```

Now you can parse the XML using JAXB:

```java
File file = new File("message.xml");
JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
Message message = (Message) jaxbUnmarshaller.unmarshal(file);
```

Sometimes you don't want to have the whole XML document, but search for a specific node, you can use XPath for that:

```java
XPath xPath = XPathFactory.newInstance().newXPath();
String expression = "/message/attachments/files[1]";
InputSource source = new InputSource(new FileInputStream(file));
String firstFile = xPath.evaluate(expression, source);
```

Or with multiple elements:

```java
String expression = "/message/attachments/files";
NodeList nodes = (NodeList) xPath.evaluate(expression, source, XPathConstants.NODESET);

for (int i = 0; i < nodes.getLength(); i++) {
  Node node = nodes.item(i);
  System.out.println(node.getTextContent());
}
```

### Exercises

Create a Java app which can read the attached [XML](./assets/person.xml) file and prints the user details to the console.

Next, please use XPath to get the following details from the [Bookstore XML file](./assets/bookstore.xml):

- The first book's title
- The last book's year
- List of book authors which are more expensive than $45
- List of book titles which in the web category
- Number of books which are in Hungarian

### XSD

Use IntelliJ IDE to generate XSD for the XML files above and check the generated files.

### JSON API

You have part of the RedditAPI implemented, your job is to add more API endpoints to it.

Open the [project](./workshop/myredditapi) and check out the code. Check out the Controllers and Services, there are is some implementation ready to use for you.

#### Posts API

Create the following API. Try to follow the pattern from the `SubredditAPIController` and use the already implemented methods in `PostService`.

#### List every Post

`GET /api/posts`

Returns every post in order of the date and time they're created.

**Response**

```json
[
    {
        "id": 1,
        "title": "First post title",
        "url": "First post content",
        "subredditId": 1,
        "subredditURL": "http://localhost:8080/api/subreddits/1",
        "score": 2
    },
    {
        "id": 2,
        "title": "Second post title",
        "url": "Second post content",
        "subredditId": 1,
        "subredditURL": "http://localhost:8080/api/subreddits/1",
        "score": 0
    },
    {
        "id": 3,
        "title": "Third post title",
        "url": "Third post content",
        "subredditId": 2,
        "subredditURL": "http://localhost:8080/api/subreddits/2",
        "score": 1
    }
]
```
#### Add new Post

`POST /api/posts`

**Request Headers**

`Content-Type: application/json`

**Request Payload**

```json
{
    "title": "Test post title",
    "url": "Test post content",
    "subredditId": 1
}
```

**Response**

- `204 No Content` if succeeded
- `400 Bad Request` if `title`, `url` or `subredditId` is missing

#### Get Post

`GET /api/posts/{id}`

**Response**

```json
{
    "id": 1,
    "title": "First post title",
    "url": "First post content",
    "subredditId": 1,
    "subredditURL": "http://localhost:8080/api/subreddits/1",
    "score": 2
}
```

- `400 Bad Request` if `id` is not valid

#### Edit Post

`PUT /api/posts/{id}`

**Request Headers**

`Content-Type: application/json`

**Request Payload**

```json
{
    "title": "Test post title",
    "url": "Test post content"
}
```

**Response**

- `204 No Content` if succeeded
- `400 Bad Request` if `title` or `url` is missing

#### Delete Post

`DELETE /api/posts/{id}`

**Response**

- `204 No Content` if succeeded
- `400 Bad Request` if `id` is not valid

### Votes API 💪

There is no service method implemented for these endpoints, it's your job to create one.

`PUT /api/posts/{id}/vote`

**Request Headers**

`Content-Type: application/json`

**Request Payload**

```json
{
    "direction": "up"|"down"
}
```

**Response**

- `204 No Content` if succeeded
- `400 Bad Request` if `id` or `direction` is not valid

### Users API 💪💪💪

This time you don't have anything implemented for you, try to add similar `GET`, `POST`, `PUT` and `DELETE` endpoints to list, add, update and delete users, respectively.
