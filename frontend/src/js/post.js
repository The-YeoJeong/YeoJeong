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
  <summary><input class="date-card__title" placeholder="1일차" /><i class="fa-solid fa-xmark"></i></summary>
  <div class="schedule-card-container">
    <fieldset class="schedule-card">
      <div class="schedule-card__location">
        <i class="fa-solid fa-xmark"></i>
        <label class="location__name" for="location__name">장소명</label>
        <input id="location__name" type="text" />
        <label class="location__addr" for="location__addr">주소</label>
        <input id="location__addr" type="text" disabled />
      </div>
      <label class="memo" for="memo">메모</label>
      <textarea
        id="memo"
        name="story"
        rows="5"
        cols="33"
        placeholder="[작성예시]
총 비용 : 25000원 
이동수단 : 버스 
주변에 공원 있으니까 사진 찍기
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
          <input id="location__addr" type="text" disabled placeholder="주소 검색"/>
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

const makeScheduleCardNode = scheduleCards => {
  return scheduleCards
    .map(
      scheduleCard =>
        `<fieldset class="schedule-card">
     <div class="schedule-card__location">
       <label class="location__name" for="location__name" disabled>장소명:</label>
       <input id="location__name" value="${scheduleCard.placeName}" type="text" disabled/>
       <label class="location__addr" for="location__addr"disabled >주소:</label>
       <input id="location__addr" value="${scheduleCard.placeAddress}" type="text" disabled />
       </div>
     <label class="memo" for="memo">메모</label>
     <textarea
       id="memo"
       name="story"
       rows="5"
       cols="33"
       disabled
       >${scheduleCard.placeContent}</textarea>
   </fieldset>`
    )
    .join('');
};

const makeDetailCardNode = dateCards => {
  return dateCards
    .map(
      dateCard =>
        `<details class="date-card">
      <summary>${dateCard.postDateCardTitle}</summary>
      <div class="schedule-card-container">
      ${makeScheduleCardNode(dateCard.postScheduleCard)}
        </div>
       </details>`
    )
    .join('');
};

//detail page
const detailPost = async (cardcontainer, id) => {
  const { data } = await axios.get(`/api/post/detail/${id}`);

  const detailPostData = `<div class="post" data-id=${data.postNo}>
  <span class="post__title">${data.postTitle}</span>
  <div class="wrapper">
  <span class="post__user">${data.memberNickname}(${data.memberId})</span>
  <span class="heart-wrapper"> <i class="fas fa-heart"></i><span class="post__likenum">${
    data.heartCnt
  }</span class="post__date">${data.createdTime.substring(0, 10)} </span>
  </div>
  <div class="post__info">
  <span>여행 기간 : </span>
  <span>${data.postStartDate.substring(0, 10)} ~ ${data.postEndDate.substring(0, 10)}</span>
  <span>여행 지역 : ${data.postRegionName}</span>
  </div>
  </div>`;
  cardcontainer.insertAdjacentHTML('beforeend', detailPostData);
  cardcontainer.innerHTML += makeDetailCardNode(data.postDateCard);
  if (data.postContent !== null) {
    cardcontainer.insertAdjacentHTML('afterend', `<div class="review">${data.postContent}</div>`);
  }
};

const commentList = async (container, id) => {
  const { data } = await axios.get(`/api/comment/${id}`);
  const comments = data
    .map(
      comment =>
        `<div data-id=${comment.commentNo}>
      <div>댓글 번호 : ${comment.commentNo}</div>
      <div>등록 날짜 : ${comment.createdTime.substring(0, 10)}</div>
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
