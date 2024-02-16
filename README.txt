Техническое задание: Social Media Application

Цель проекта: Разработать RESTful API для социальной медиа платформы,
позволяющей пользователям регистрироваться, входить в систему, создавать посты, переписываться,
подписываться на других пользователей и получать свою ленту активности.

Требования:
1. Аутентификация и авторизация:
- Пользователи могут зарегистрироваться, указав имя пользователя, электронную почту и пароль.
- Пользователи могут войти в систему, предоставив правильные учетные данные.
- АРІ должен обеспечивать защиту
конфиденциальности пользовательских данных, включая хэширование паролей и использование
JWT.
1. Управление постами:
- Пользователи могут создавать новые посты, указывая текст, заголовок.
- Пользователи могут просматривать посты других пользователей.
- Пользователи могут обновлять и удалять свои собственные посты.
2. Взаимодействие пользователей:
- Пользователи могут отправлять заявки в друзья другим пользователям. С этого момента, пользователь, отправивший заявку, остается подписчиком до тех пор, пока сам не откажется от подписки. Если пользователь, получивший заявку, принимает ее, оба пользователя становятся друзьями. Если отклонит, то пользователь, отправивший заявку, как и указано ранее, все равно остается подписчиком.
- Пользователи, являющиеся друзьями, также являются подписчиками друг на друга.
- Если один из друзей удаляет другого из друзей, то он также отписывается. Второй пользователь при этом должен остаться подписчиком.
- Друзья могут писать друг другу сообщения (реализация чата не нужна, пользователи могу запросить переписку с помощью запрос а)
3. Подписки и лента активности:
- Лента активности пользователя должна отображать последние посты от пользователей, на которых он подписан.
- Лента активности должна поддерживать пагинацию и сортировку по времени создания постов.
4. Обработка ошибок:
- АРІ должно обрабатывать и возвращать понятные
сообщения об ошибках при неправильном запросе или внутренних проблемах сервера.
- АРІ должно осуществлять валидацию введенных
данных и возвращать информативные сообщения при неправильном формате

Технологии и инструменты:
- Язык программирования: Java
- Фреймворк: Spring (рекомендуется использовать
Spring Boot)
- База данных: PostgreSQL
- Аутентификация и авторизация: Spring Security
- Документация API: Swagger(в процессе реализации)

Registration: /auth/registration POST
Body example:
{
    "username" : "adam",
    "email" : "adam@mail.com",
    "password" : "1",
    "name" : "Adam"
}

Login - /auth/login POST
Body example:
{
    "username" : "sam",
    "password" : "1"
}

**************************************

You can manipulate registered people

People list: /api/people GET

Find person: /api/people/{person_id} GET

Update person: /api/people/{person_id} PUT
Body example:
{
    "name" : "new_name"
}

Delete person: /api/people/{person_id} DELETE

**************************************

You can create posts

Posts list: /api/posts GET

Find post by person: /api/posts/{person_id} GET

Create post: /api/posts POST
Body example:
{
    "title" : "Adam's first post!",
    "description" : "It's my first post!"
}

Update post: /api/posts/{post_id} PUT
Body example:
{
    "title" : "updated_post",
    "description" : "updated_post"
}

Delete post: /api/posts/{post_id} DELETE

**************************************

You can add friends and manipulate friendships

Friendships list: /api/friendships GET

Find friendship: /api/friendships/{friendship_id} GET

Find friendship by person: /api/friendships/people?personId={person_id} GET

Accept friendship: /api/friendships?friendshipId={friendship_id} PUT

Subscribe for a person: /api/friendships?personId={person_id} POST

Cancel subscription or friendship: /api/friendships?friendshipId={friendship_id} DELETE

**************************************

You can send and manipulate messages

Get messages: /api/messages GET

Get chat with a person: /api/messages/chat?personId={person_id} GET

Send message: /api/messages?personId={person_id} POST
Body example:
{
    "content" : "Hello!"
}

Edit message: /api/messages/{message_id} PUT
Body example:
{
    "content" : "Updated message content"
}

Delete messages: /api/messages/{message_id} DELETE

**************************************

Feeds

You can get the latest created posts of users

Get latests feeds: /api/feeds GET