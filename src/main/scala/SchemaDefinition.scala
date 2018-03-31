import sangria.schema._

object SchemaDefinition {

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
        Field("tags", ListType(StringType),
          Some("記事についているタグ"),
          resolve = _.value.tags)
      ))
  // このように記述することもできます
  // import sangria.macros.derive._
  // val Article = deriveObjectType[ArticleRepository, Article]

  // Queryに使用する引数です これはString型のidという名前を持つ引数
  val idArgument = Argument("id", StringType, description = "id")

  // Queryです。ここにクエリ操作を定義していきます。
  val QueryType = ObjectType(
    "Query", fields[ArticleRepository, Unit](
      Field("article", OptionType(ArticleType),
        arguments = idArgument :: Nil,
        // ArticleRepositoryからどのように記事を取得するかを記述しています(ctx.ctxはArticleRepository型)
        resolve = ctx => ctx.ctx.findArticleById(ctx.arg(idArgument))),
      Field("articles", ListType(ArticleType),
        resolve = ctx => ctx.ctx.findAllArticles)
    )
  )

  // 最後にSchemaを定義します。
  val ArticleSchema = Schema(QueryType)
}
