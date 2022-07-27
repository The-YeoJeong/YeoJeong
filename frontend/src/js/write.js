import write from '../html/write.html';

const writeNode = () => {
  const node = document.createElement('div');
  node.innerHTML = write;
  // Event

  const $cardContainer = node.querySelector('.card-container');

  node.querySelector('.add-date-card-button').addEventListener('click', () => {
    console.log($cardContainer);
    $cardContainer.innerHTML += `<details class="date-card">
    <summary><input class="data-card__title" placeholder="1일차" /><i class="fa-solid fa-xmark"></i></summary>
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
      <button class="schedule-card__button">저장</button>
    </fieldset>
    <div class="date-card__buttons">
      <button class="saveAll-button">전체 저장</button>
      <button class="add-schedule-button">+</button>
    </div>
  </details>`;
  });

  return node.children;
};

export default writeNode;
