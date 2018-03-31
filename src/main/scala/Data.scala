case class Article(id: String, title: String, author: Option[String], tags: List[String])

class ArticleRepository {
  def findArticleById(id: String): Option[Article] = ArticleRepository.articles.find(_.id == id)

  def findAllArticles: List[Article] = ArticleRepository.articles
}

object ArticleRepository {
  val articles = List(
    Article("1", "AWSの話", Some("yamasaki"), List("aws", "インフラ")),
    Article("2", "Scalaの話", None, List("null", "許さない")))
}
