import axios from 'axios';

//main page
const top3posts = async container => {
  const { data } = await axios.get('/api/main/post/top');
  const top3post = data
    .map(
      post =>
        `  <div class="top-post" data-id=${post.postNo}>
    <div class="wrapper">
      <span class="top-post__title">${post.postTitle}</span>
      <span class="top-post__heart-wrapper"> <i class="fas fa-heart"></i><span class="top-post__likenum">${post.postHeartCnt}</span> </span>
    </div>
    <span class="top-post__user">${post.memberNickname}님</span>
    <img class="top_post__img"></img>
  </div>`
    )
    .join('');
  container.innerHTML = top3post;
};

//write page
const addDataCard = container => {
  container.insertAdjacentHTML(
    'beforeend',
    `<details class="date-card">
  <summary><input class="data-card__title" placeholder="1일차" /><i class="fa-solid fa-xmark"></i></summary>
  <div class="schedule-card-container">
    <fieldset class="schedule-card">
      <div class="schedule-card__location">
        <i class="fa-solid fa-xmark"></i>
        <label class="location__name" for="location__name">장소명</label>
        <input id="location__name" type="text" />
        <label class="location__addr" for="location__addr">주소</label>
        <input id="location__addr" type="text" disabled />
        <i class="fas fa-magnifying-glass"></i>
      </div>
      <label class="memo" for="memo">메모</label>
      <textarea
        id="memo"
        name="story"
        rows="5"
        cols="33"
        placeholder="[작성예시]
총 비용 : 25000원 
이동수단 : 버스 주변에 공원 있으니까 사진 찍기
          "
      ></textarea>
    </fieldset>
  </div>
  <div class="date-card__buttons">
    <button class="add-schedule-button">+</button>
  </div>
</details>`
  );
};

const addScheduleCard = container => {
  container.insertAdjacentHTML(
    'beforeend',
    `<fieldset class="schedule-card">
        <div class="schedule-card__location">
          <i class="fa-solid fa-xmark"></i>
          <label class="location__name" for="location__name">장소명</label>
          <input id="location__name" type="text" />
          <label class="location__addr" for="location__addr">주소</label>
          <input id="location__addr" type="text" disabled />
          <i class="fas fa-magnifying-glass"></i>
        </div>
        <label class="memo" for="memo">메모</label>
        <textarea
          id="memo"
          name="story"
          rows="5"
          cols="33"
          placeholder="[작성예시]
총 비용 : 25000원
이동수단 : 버스 주변에 공원 있으니까 사진 찍기
              "
        ></textarea>
      </fieldset>`
  );
};

//detail page
const detailPost = async (container, id) => {
  const { data } = await axios.get(`/api/post/detail/${id}`);
  const detailPostData = 
    `<div class="top-post" data-id=${data.postNo}>
      <div class="wrapper">
        <span class="top-post__title">${data.postTitle}</span>
        <span class="heart-wrapper"> <i class="fas fa-heart"></i><span class="top-post__likenum">${data.heartCnt}</span> </span>
      </div>
      <span class="top-post__user">${data.memberNickname}님</span>
      <img class="top_post__img"></img>
      <div>등록 날짜 : ${data.createdTime.substring(0,10)}</div>
      <div>시작 날짜 : ${data.postStartDate.substring(0,10)}</div>
      <div>끝 날짜 : ${data.postEndDate.substring(0,10)}</div>
      <div>좋아요 수 : ${data.heartCnt}</div>
      <div>지역 : ${data.postRegionName}</div>
      <div>일자 카드 : ${data.postDateCard[0].postDateCardTitle}</div>
      <div>일정 카드 : ${data.postDateCard[0].postScheduleCard[0].placeName}</div>
      <div>나만보기 : ${data.postOnlyMe}</div>
      <div>후기 : ${data.postContent}</div>
    </div>`
  container.innerHTML
  document.querySelector('.post_title')
  console.log('dfsa');
  console.log(data);
  console.log(data.heartCnt);
  container.innerHTML = detailPostData;
};

const commentList = async (container, id) => {
  const { data } = await axios.get(`/api/comment/${id}`);
  const comments =  data
  .map(
    comment =>
    `<div data-id=${comment.commentNo}>
      <div>댓글 번호 : ${comment.commentNo}</div>
      <div>등록 날짜 : ${comment.createdTime.substring(0,10)}</div>
      <div>아이디 : ${comment.memberId}</div>
      <div>닉네임 : ${comment.memberNickname}</div>
      <div>내용 : ${comment.commentContent}</div>
      <hr>
    </div>`
  )
  .join('');
  console.log(data);
  container.innerHTML = comments;
};


export default {
  top3posts,
  addDataCard,
  addScheduleCard,
  detailPost,
  commentList,
};