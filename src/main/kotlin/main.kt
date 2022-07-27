data class Post(
    val id: Int = 0,            // Идентификатор записи
    val ownerId: Int = 123,     // Идентификатор владельца стены
    val fromId: Int = 321,      // Идентификатор автора записи
    val createdBy: Int = 46,    //Идентификатор администратора, который опубликовал запись
    val date: Int = 36000,      // Время публикации записи в формате unixtime
    val text: String = "Empty", // Текст записи
    val replyOwnerId: Int = 654,    // Идентификатор владельца записи, в ответ на которую была оставлена текущая
    val replyPostId: Int = 987,     // Идентификатор записи, в ответ на которую была оставлена текущая
    val friendsOnly: Boolean = true,    // true, если запись была создана с опцией «Только для друзей»
    val comments: Comments?,    // комментарии к записи
    val likes: Likes?,          // лайки к записи
    val canPin: Boolean = true, //может ли текущий пользователь закрепить запись (true — может, false — не может).
    val canDelete: Boolean = true,  //может ли текущий пользователь удалить запись (true — может, false — не может).
    val canEdit: Boolean = true,    //может ли текущий пользователь редактировать запись (true — может, false — не может).
    val isPinned: Int = 0,      // Информация о том, что запись закреплена
    val markedAsAds: Boolean = false,   // содержит ли запись отметку «реклама» (true — да, false — нет)
    val isFavorite: Boolean = false,    // true, если объект добавлен в закладки у текущего пользователя
    val postponedId: Int = 0,   // Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере
    val attachment: Attachment?
)

data class Comments(
    val count: Int,         //количество комментариев
    val canPost: Boolean,   // информация о том, может ли текущий пользователь комментировать запись (true — может, false — не может)
    val groupsCanPost: Boolean, // информация о том, могут ли сообщества комментировать запись
    val canClose: Boolean,  // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean    // может ли текущий пользователь открыть комментарии к записи.
)

data class Likes(
    val count: Int,         //число пользователей, которым понравилась запись
    val userLikes: Boolean, //наличие отметки «Мне нравится» от текущего пользователя (true — есть, false — нет)
    val canLike: Boolean,   //может ли текущий пользователь поставить отметку «Мне нравится» (true — может, false — не может);
    val canPublish: Boolean //может ли текущий пользователь сделать репост записи (true — может, false — не может).
)

data class Comment(
    val id: Int = 0,                //Идентификатор комментария
    val fromId: Int = 0,            //Идентификатор автора комментария
    val date: Int = 3000,           //Дата создания комментария в формате Unixtime
    val text: String = "Text comment", //Текст комментария
    val replyToUser: Int?,          // Идентификатор пользователя или сообщества, в ответ которому оставлен текущий комментарий (если применимо)
    val replyToComment: Int?,       //Идентификатор комментария, в ответ на который оставлен текущий (если применимо)
    val attachments: Attachment?,   // Медиавложения комментария (фотографии, ссылки и т.п.). Описание массива attachments находится на отдельной странице.
    val parentsStack: Array<Int>?,  //Массив идентификаторов родительских комментариев
    val thread: ThreadComments?,     //Информация о вложенной ветке комментариев, объект с полями:
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false
        if (fromId != other.fromId) return false
        if (date != other.date) return false
        if (text != other.text) return false
        if (replyToUser != other.replyToUser) return false
        if (replyToComment != other.replyToComment) return false
        if (attachments != other.attachments) return false
        if (parentsStack != null) {
            if (other.parentsStack == null) return false
            if (!parentsStack.contentEquals(other.parentsStack)) return false
        } else if (other.parentsStack != null) return false
        if (thread != other.thread) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + fromId
        result = 31 * result + date
        result = 31 * result + text.hashCode()
        result = 31 * result + (replyToUser ?: 0)
        result = 31 * result + (replyToComment ?: 0)
        result = 31 * result + (attachments?.hashCode() ?: 0)
        result = 31 * result + (parentsStack?.contentHashCode() ?: 0)
        result = 31 * result + (thread?.hashCode() ?: 0)
        return result
    }
}

data class ThreadComments(
    val count: Int,         //количество комментариев в ветке
    val items: Array<Comment>, //массив объектов комментариев к записи (только для метода wall.getComments).
    val canPost: Boolean,      //может ли текущий пользователь оставлять комментарии в этой ветке
    val showReplyButton: Boolean,   //нужно ли отображать кнопку «ответить» в ветке
    val groupsCanPost: Boolean      //могут ли сообщества оставлять комментарии в ветке
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ThreadComments

        if (count != other.count) return false
        if (!items.contentEquals(other.items)) return false
        if (canPost != other.canPost) return false
        if (showReplyButton != other.showReplyButton) return false
        if (groupsCanPost != other.groupsCanPost) return false

        return true
    }

    override fun hashCode(): Int {
        var result = count
        result = 31 * result + items.contentHashCode()
        result = 31 * result + canPost.hashCode()
        result = 31 * result + showReplyButton.hashCode()
        result = 31 * result + groupsCanPost.hashCode()
        return result
    }
}

class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()

    fun add(post: Post): Post {
        posts += post.copy(id = post.hashCode())
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, oldPost) in posts.withIndex()) {
            if (post.id == oldPost.id) {
                posts[index] = oldPost.copy(
                    post.id,
                    oldPost.ownerId,    // Идентификатор владельца стены
                    post.fromId,
                    post.createdBy,
                    oldPost.date,       // Время публикации записи в формате unixtime
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
                    post.postponedId
                )
                return true
            }
        }
        return false
    }

    fun findById(id: Int): Post? {
        for (post in posts) {
            if (post.id == id) {
                return post
            }
        }
        return null
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for ((index, post) in posts.withIndex()) {
            if (post.id == postId) {
                comments += comment
                posts[index] = post.copy(
                    comments = Comments(
                        count = (post.comments?.count ?: 0) + 1,
                        canPost = post.comments?.canPost ?: false,
                        groupsCanPost = post.comments?.groupsCanPost ?: false,
                        canClose = post.comments?.canClose ?: false,
                        canOpen = post.comments?.canOpen ?: false
                    )
                )
            }
        }
        if (findById(postId) == null) throw PostNotFoundException("No post with id $postId")
        return comments.last()
    }
}

fun main() {

}