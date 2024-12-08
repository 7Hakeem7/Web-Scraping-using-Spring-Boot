# **Web Scraping with Trie**
A Spring Boot-based application for web scraping and keyword management using a Trie data structure, with support for prefix-based searching and scheduled tasks.

# **Features**
🕷️ Web scraping: Scrapes web pages for specified keywords.
📂 Trie-based search: Efficient prefix-based keyword search.
📅 Scheduled scraping: Automates scraping tasks at regular intervals.
🛠️ REST APIs: Four endpoints for managing scraping tasks and results.
# **Technologies Used**
- Java 17
+ Spring Boot 3.1.0
* JSoup (for HTML parsing)
- Mockito and JUnit 5 (for testing)

# **Setup Instructions**
Clone the repository
bash
Copy code
git clone https://github.com/<your-username>/web-scraping-with-trie.git
Navigate to the project directory
bash
cd web-scraping-with-trie
Build the project
bash
mvn clean install
Run the application
bash
mvn spring-boot:run
Access the application at: http://localhost:8081
# **Endpoints**
### 1️⃣ **Scrape URL for Keywords**
URL: /scrape
Method: POST
Description: Scrapes the provided URL for specified keywords and stores metadata.
Request Body:
json
{
  "url": "https://jsoup.org",
  "keywords": ["java", "opensource"]
}
Response:
json
{
  "message": "Scraping completed successfully."
}
### 2️⃣ **Get All Scraped Data**
URL: /scraped-data
Method: GET
Description: Retrieves all data scraped so far, including URLs, matched content, and timestamps.
Response:
json
[
  {
    "keyword": "java",
    "url": "https://jsoup.org",
    "matchedContent": "Java library that simplifies working...",
    "timestamp": "2024-12-05T10:15:30"
  }
]
### 3️⃣ **Search by Prefix**
URL: /search
Method: GET
Description: Searches for keywords starting with the specified prefix and returns the matching data.
Query Parameters:
prefix: The prefix to search for.
limit: Maximum number of results to return.
Example:
bash
/search?prefix=java&limit=2
Response:
json
[
  {
    "keyword": "java",
    "url": "https://jsoup.org",
    "matchedContent": "Java library that simplifies working...",
    "timestamp": "2024-12-05T10:15:30"
  }
]
### 4️⃣ **Scheduled Scraping**
Description: Automatically scrapes predefined URLs at regular intervals (1 hour by default).
Setup: Add URLs and keywords to the scheduledUrls and scheduledKeywords lists in the code.
Logs: Scheduled scraping logs are available in the application console.

# **Testing**
To run unit tests:

bash
mvn test

Tests include:

Mocking the Trie behavior.
Validating scraped data retrieval.
Ensuring prefix-based search functionality.

Directory Structure
web-scraping-with-trie
├── src/main/java/com/example/web_scraping_with_trie
│   ├── controller        # REST API controllers
│   ├── service           # Business logic
│   ├── model             # Data models
│   └── repository        # Persistence layer
├── src/test/java/com/example/web_scraping_with_trie
├── resources
│   └── application.properties
├── pom.xml
└── README.md
Future Enhancements
🔒 Add authentication for endpoints.
🗄️ Integrate a persistent database for better scalability.
🌐 Create a front-end interface for improved usability.

License
This project is licensed under the MIT License.
See the LICENSE file for details.

Contact
📧 Hakeem Syed
✉️ Email: 7hakeemsyed7@gmail.com
🌐 LinkedIn: https://www.linkedin.com/in/syed-hakeem7
