data class Post(
    val id: Int = 0,         // Идентификатор записи
    val ownerId: Int = 123,    // Идентификатор владельца стены
    val fromId: Int = 321,     // Идентификатор автора записи
    val createdBy: Int = 46,  //Идентификатор администратора, который опубликовал запись
    val date: Int = 36000,       // Время публикации записи в формате unixtime
    val text: String = "Empty",    // Текст записи
    val replyOwnerId: Int = 654,   // Идентификатор владельца записи, в ответ на которую была оставлена текущая
    val replyPostId: Int = 987,    // Идентификатор записи, в ответ на которую была оставлена текущая
    val friendsOnly: Boolean = true,    // true, если запись была создана с опцией «Только для друзей»
    val comments: Comments?,
    // комментарии к записи
    val likes: Likes?,
    // лайки к записи
    val canPin: Boolean = true,    //может ли текущий пользователь закрепить запись (true — может, false — не может).
    val canDelete: Boolean = true, //может ли текущий пользователь удалить запись (true — может, false — не может).
    val canEdit: Boolean = true,   //может ли текущий пользователь редактировать запись (true — может, false — не может).
    val isPinned: Int = 0,      // Информация о том, что запись закреплена
    val markedAsAds: Boolean = false,   // содержит ли запись отметку «реклама» (true — да, false — нет)
    val isFavorite: Boolean = false,     // true, если объект добавлен в закладки у текущего пользователя
    val postponedId: Int = 0, // Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере
    val attachment: Attachment?
    )

data class Comments (
    val count: Int,          //количество комментариев
    val canPost: Boolean,    // информация о том, может ли текущий пользователь комментировать запись (true — может, false — не может)
    val groupsCanPost: Boolean,  // информация о том, могут ли сообщества комментировать запись
    val canClose: Boolean,   // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean    // может ли текущий пользователь открыть комментарии к записи.
)

data class Likes (
    val count: Int,          //число пользователей, которым понравилась запись
    val userLikes: Boolean,  //наличие отметки «Мне нравится» от текущего пользователя (true — есть, false — нет)
    val canLike: Boolean,    //может ли текущий пользователь поставить отметку «Мне нравится» (true — может, false — не может);
    val canPublish: Boolean //может ли текущий пользователь сделать репост записи (true — может, false — не может).
)

class WallService {
    private var posts = emptyArray<Post>()

    fun add (post: Post): Post {
        posts += post.copy(id = post.hashCode())
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index,oldPost) in posts.withIndex()){
            if (post.id == oldPost.id){
                posts[index] = oldPost.copy(
                    post.id,
                    oldPost.ownerId,    // Идентификатор владельца стены
                    post.fromId,
                    post.createdBy,                    oldPost.date,       // Время публикации записи в формате unixtime
                    post.text,
                    post.replyOwnerId,
                    post.replyPostId,
                    post.friendsOnly,
                    post.comments,
                    post.likes,
                    post.canPin,
                    post.canDelete,
                    post.canEdit,
                    post.isPinned,
                    post.markedAsAds,
                    post.isFavorite,
                    post.postponedId,
                )
                return true
            }
        }
        return false
    }
}
fun main() {

}