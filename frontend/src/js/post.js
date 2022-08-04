import axios from 'axios';

let user = '';

(async () => {
  const { data } = await axios.get('/api/member/get/me', {
    headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
  });
  user = data;
})();

//main page
const renderTop3posts = async container => {
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

const renderPosts = (data, container) => {
  const posts = data
    .map(
      post => `<div class="mainpost" data-id="${post.postNo}">
      <div class="mainpost__left">
        <span class="mainpost__user">${post.memberNickname}(${post.memberId})</span>
        <span class="mainpost__title">${post.postTitle}</span>
        <div class="mainpost-wrapper">
          <i class="fas fa-heart main" style="color: orangered"></i><span class="mainpost__heartnum">${
            post.postHeartCnt
          }</span
          ><span class="mainpost__date">${post.createdTime.substring(0, 10)}</span>
        </div>
      </div>
      <img class="mainpost__img" scr="" /></div>`
    )
    .join('');
  container.innerHTML = posts;
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

let positions = [];

const makePositions = dateCards => {
  dateCards.forEach(dateCard => {
    dateCard.postScheduleCard.forEach(schedule => {
      positions.push({ no: schedule.postSchedulecardNo, addr: schedule.placeAddress, addr_name: schedule.placeName });
    });
  });
  console.log(positions);
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
  console.log(makePositions(dateCards));
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
const detailPost = async (cardcontainer, id, nickName) => {
  const { data } = await axios.get(`/api/post/detail/${id}`);
  console.log('tetete', data);
  console.log('?', user.memberNickname, data.memberNickname);
  console.log(nickName, data.memberNickname);
  if (data.memberNickname === nickName) {
    document.querySelector('.detail-buttons').classList.remove('hidden');
    if (data.liked) {
      document.querySelector('.fas.fa-heart').classList.add('liked');
    }
  }

  document.querySelector('.detailpost').dataset.id = data.postNo;
  document.querySelector('.detailpost__title').textContent = data.postTitle;
  document.querySelector('.detailpost__user').textContent = `${data.memberNickname}(${data.memberId})`;
  document.querySelector('.post__likenum').textContent = data.heartCnt;
  document.querySelector('.detailpost__date').textContent = data.createdTime.substring(0, 10);
  document.querySelector('.date').textContent = `여행 기간 : ${data.postStartDate.substring(
    0,
    10
  )} ~ ${data.postEndDate.substring(0, 10)}`;
  document.querySelector('.cities').textContent = `여행 지역 : ${data.postRegionName}`;

  cardcontainer.innerHTML += makeDetailCardNode(data.postDateCard);

  if (data.postContent !== null) {
    document.querySelector('.review').innerHTML = `<span>후기</span>${data.postContent}`;
  }
};

const commentRender = async (container, id) => {
  const { data } = await axios.get(`/api/comment/${id}`);
  console.log('d', data);
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
  container.innerHTML = comments;
};

export default {
  renderTop3posts,
  addDataCard,
  addScheduleCard,
  detailPost,
  renderPosts,
  commentRender,
};
