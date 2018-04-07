import sangria.relay._
import sangria.schema._

object SchemaDefinition {

  // GraphQL上のComment型を定義しています。
  val CommentType =
    ObjectType(
      "Comment",
      "コメント",
      fields[ArticleRepository, Comment](
        Field("id", StringType, Some("コメントのId"), resolve = _.value.id),
        Field("articleId",
          StringType,
          Some("コメントがついている記事のId"),
          resolve = _.value.articleId),
        Field("body", StringType, Some("コメントの本文"), resolve = _.value.body)
      )
    )

  //GraphQL上でのCommentConnection型を定義します
  val ConnectionDefinition(_, commentConnection) =
    Connection.definition[ArticleRepository, Connection, Comment]("Comments",
      CommentType)

  // GraphQL上のArticle型を定義しています。
  val ArticleType =
    ObjectType(
      "Article",
      "記事",
      fields[ArticleRepository, Article](
        Field("id", StringType,
          Some("記事のId"),
          // Scalaコード中のArticle型とのマッピングを記述しています(_.valueはArticle型)
          resolve = _.value.id),
        Field("title", StringType,
          Some("記事のタイトル"),
          resolve = _.value.title),
        Field("author", OptionType(StringType),
          Some("記事の著者"),
          resolve = _.value.author
        ),
        // commentsという要素で関連するCommentを取得するようにしています。
        Field("comments", OptionType(commentConnection),
          Some("記事についているコメント"),
          arguments = Connection.Args.All,
          resolve = ctx => ctx.ctx.commentConnection(ctx.value.id, ConnectionArgs(ctx))),
        Field("tags", ListType(StringType),
          Some("記事についているタグ"),
          resolve = _.value.tags)
      ))
  // このように記述することもできます
  //  import sangria.macros.derive._
  //  val ArticleType = deriveObjectType[ArticleRepository, Article](
  //    AddFields(
  //      Field(
  //        "comments",
  //        OptionType(commentsConnection),
  //        Some("記事についているコメント"),
  //        arguments = Connection.Args.All,
  //        resolve =
  //          ctx => ctx.ctx.commentsConnection(ctx.value.id, ConnectionArgs(ctx))
  //      ),
  //    )
  //  )

  //GraphQL上でのArticleConnection型を定義します
  val ConnectionDefinition(_, articleConnection) =
    Connection.definition[ArticleRepository, Connection, Article]("Article",
      ArticleType)

  // Queryに使用する引数です これはString型のidという名前を持つ引数
  val idArgument = Argument("id", StringType, description = "id")

  // Queryです。ここにクエリ操作を定義していきます。
  val QueryType = ObjectType(
    "Query",
    fields[ArticleRepository, Unit](
      Field(
        "article",
        OptionType(ArticleType),
        arguments = idArgument :: Nil,
        // ArticleRepositoryからどのように記事を取得するかを記述しています(ctx.ctxはArticleRepository型)
        resolve = ctx => ctx.ctx.findArticleById(ctx.arg(idArgument))),
      Field("articles",
        OptionType(articleConnection),
        arguments = Connection.Args.All,
        resolve = ctx => ctx.ctx.articleConnection(ConnectionArgs(ctx)))
    )
  )

  // 最後にSchemaを定義します。
  val ArticleSchema = Schema(QueryType)
}
