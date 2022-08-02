import axios from 'axios';

const top3posts = async container => {
  const { data } = await axios.get('/api/main/post/top');
  const top3post = data
    .map(
      post =>
        `  <div class="top-post" id=${post.postNo}>
    <div class="wrapper">
      <span class="top-post__title">${post.postTitle}</span>
      <span class="heart-wrapper"> <i class="fas fa-heart"></i><span class="top-post__likenum">${post.postHeartCnt}</span> </span>
    </div>
    <span class="top-post__user">${post.memberNickname}님</span>
    <img class="top_post__img"></img>
  </div>`
    )
    .join('');
  container.innerHTML = top3post;
};

export default {
  top3posts,
};
