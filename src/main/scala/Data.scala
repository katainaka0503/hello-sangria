import sangria.relay.{ Connection, ConnectionArgs }

case class Article(id: String, title: String, author: Option[String], tags: List[String])

case class Comment(id: String, articleId: String, body: String)

class ArticleRepository {
  import ArticleRepository._
  def findArticleById(id: String): Option[Article] = ArticleRepository.articles.find(_.id == id)

  //メモリ内のコレクションを使用した実装
  //実際にはafter,before,first,lastを使用してDB等にどのようなクエリでアクセスするかを決定しデータを取得
  //データが取得できたらページング情報とともにデータを返すようにする
  def articleConnection(connectionArgs: ConnectionArgs): Connection[Article] =
    Connection.connectionFromSeq(articles, connectionArgs)

  def commentConnection(articleId: String, connectionArgs: ConnectionArgs): Connection[Comment] =
    Connection.connectionFromSeq(comments.filter(_.articleId == articleId), connectionArgs)
}

object ArticleRepository {
  val articles = List(
    Article("1", "AWSの話", Some("yamasaki"), List("aws", "インフラ")),
    Article("2", "Scalaの話", None, List("null", "許さない")),
    Article("3", "Scalaの話2", None, List("null", "許さない")),
    Article("4", "Scalaの話3", None, List("null", "許さない")),
    Article("5", "TaPLの話", None, List()),
    Article("6", "動的型付言語に目覚めた話", None, List()),
    Article("7", "静的型付言語に戻ってきた話", None, List()),
    Article("8", "Haskellの話", None, List()),
    Article("9", "Haskellの話2", None, List()),
    Article("10", "F#の話", None, List()),
    Article("11", "ボツ記事2", None, List()),
    Article("12", "Typescriptの話", None, List()),
    Article("13", "Typescriptの話2", None, List()),
    Article("14", "ボツ記事1", None, List()))

  val comments = List(
    Comment("1", "1", "だれかOCamlの話をしてくれ"),
    Comment("2", "1", "そんなことよりHaskellの話がしたい"),
    Comment("3", "2", "だれかOcamlの話をしてくれ"),
    Comment("4", "2", "そんなことよりHaskellの話がしたい")
  )
}
