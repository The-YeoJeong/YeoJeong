import write from '../html/write.html';

const writeNode = () => {
  const node = document.createElement('div');
  node.innerHTML = write;
  let $targetAddr = '';
  // Event

  $(function () {
    $('#summernote').summernote({
      height: 300,
      minHeight: null,
      maxHeight: null,
      focus: true,
      callbacks: {
        onImageUpload: function (files, editor, welEditable) {
          for (var i = 0; i < files.length; i++) {
            sendFile(files[i], this);
          }
        },
      },
      toolbar: [
        ['font', ['underline', 'clear']],
        ['fontname', ['fontname']],
        ['color', ['color']],
        ['para', ['paragraph']],
        ['height', ['height']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'hr']],
        ['view', ['codeview']],
        ['help', ['help']],
      ],
    });
    $('#plan-period-startdate').datepicker({
      dateFormat: 'yy-mm-dd',
      // 날짜의 형식
      changeMonth: true,
      // 월을 이동하기 위한 선택상자 표시여부
      minDate: 0,
      // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
      onClose: function (selectedDate) {
        // 시작일(fromDate) datepicker가 닫힐때
        // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
        $('#plan-period-enddate').datepicker('option', 'minDate', selectedDate);
      },
    });
    //종료일
    $('#plan-period-enddate').datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      minDate: 0,
      // 오늘 이전 날짜 선택 불가
      onClose: function (selectedDate) {
        // 종료일(toDate) datepicker가 닫힐때
        // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
        $('#plan-period-startdate').datepicker('option', 'maxDate', selectedDate);
      },
    });
  });

  function sendFile(file, el) {
    var form_data = new FormData();
    form_data.append('file', file);
    $.ajax({
      data: form_data,
      type: 'POST',
      url: '/api/image',
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
        $(el).summernote('insertImage', url, function ($image) {
          $image.css('width', '50%');
        });
      },
    });
  }

  let markers = [];

  // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      // 정상적으로 검색이 완료됐으면
      // 검색 목록과 마커를 표출합니다
      displayPlaces(data);

      // 페이지 번호를 표출합니다
      displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      alert('검색 결과가 존재하지 않습니다.');
      return;
    } else if (status === kakao.maps.services.Status.ERROR) {
      alert('검색 결과 중 오류가 발생했습니다.');
      return;
    }
  }

  // 장소 검색 객체를 생성합니다
  let ps = new kakao.maps.services.Places();

  // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
  let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  // 키워드 검색을 요청하는 함수입니다
  function searchPlaces() {
    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
      alert('키워드를 입력해주세요!');
      return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch(keyword, placesSearchCB);
  }

  node.querySelector('.searchLoca').addEventListener('click', () => {
    searchPlaces();
  });

  function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
      menuEl = document.querySelector('.menu_wrap'),
      fragment = document.createDocumentFragment(),
      bounds = new kakao.maps.LatLngBounds(),
      listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (var i = 0; i < places.length; i++) {
      // 마커를 생성하고 지도에 표시합니다
      var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
        marker = addMarker(placePosition, i),
        itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(placePosition);

      // 마커와 검색결과 항목에 mouseover 했을때
      // 해당 장소에 인포윈도우에 장소명을 표시합니다
      // mouseout 했을 때는 인포윈도우를 닫습니다
      (function (marker, addName) {
        kakao.maps.event.addListener(marker, 'mouseover', function () {
          displayInfowindow(marker, addName);
        });

        kakao.maps.event.addListener(marker, 'mouseout', function () {
          infowindow.close();
        });

        itemEl.onmouseover = function () {
          displayInfowindow(marker, addName);
        };

        itemEl.onmouseout = function () {
          infowindow.close();
        };

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function () {
          $targetAddr = addName;
          console.log($targetAddr);
          close();
        });
      })(marker, places[i].place_name);

      fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    mainMap.setBounds(bounds);
  }

  // 검색결과 항목을 Element로 반환하는 함수입니다
  function getListItem(index, places) {
    var el = document.createElement('li'),
      itemStr =
        '<span class="markerbg marker_' +
        (index + 1) +
        '"></span>' +
        '<div class="info">' +
        '   <h5>' +
        places.place_name +
        '</h5>';

    if (places.road_address_name) {
      itemStr +=
        '    <span>' +
        places.road_address_name +
        '</span>' +
        '   <span class="jibun gray">' +
        places.address_name +
        '</span>';
    } else {
      itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' + '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
  }

  // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
  function addMarker(position, idx) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
      imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
      imgOptions = {
        spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
        spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      },
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
      marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });

    marker.setMap(mainMap); // 지도 위에 마커를 표출합니다
    markers.push(marker); // 배열에 생성된 마커를 추가합니다

    return marker;
  }

  // 지도 위에 표시되고 있는 마커를 모두 제거합니다
  function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
  function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
      fragment = document.createDocumentFragment(),
      i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
      paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
      var el = document.createElement('a');
      el.href = '#';
      el.innerHTML = i;

      if (i === pagination.current) {
        el.className = 'on';
      } else {
        el.onclick = (function (i) {
          return function () {
            pagination.gotoPage(i);
          };
        })(i);
      }

      fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
  }

  // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
  // 인포윈도우에 장소명을 표시합니다
  function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(mainMap, marker);
  }

  // 검색결과 목록의 자식 Element를 제거하는 함수입니다
  function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
      el.removeChild(el.lastChild);
    }
  }

  function show() {
    document.querySelector('.background').className = 'background show';
  }

  function close() {
    document.querySelector('.background').className = 'background';
  }

  node.querySelector('#close').addEventListener('click', close);

  const $cardContainer = node.querySelector('.card-container');

  node.querySelector('.add-date-card-button').addEventListener('click', () => {
    $cardContainer.insertAdjacentHTML(
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
  });

  $cardContainer.addEventListener('click', e => {
    if (e.target.className === 'add-schedule-button') {
      const { previousElementSibling } = e.target.parentNode;
      previousElementSibling.insertAdjacentHTML(
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
    }
    if (e.target.className === 'schedule-card__button') {
      if (e.target.parentNode.children[2].disabled) {
        e.target.parentNode.children[0].children[2].disabled = false;
        e.target.parentNode.children[2].disabled = false;
        e.target.textContent = '저장';
      } else {
        e.target.parentNode.children[0].children[2].disabled = true;
        e.target.parentNode.children[2].disabled = true;
        e.target.textContent = '수정';
      }
    }
    if (e.target.classList.contains('fa-xmark')) {
      e.target.parentNode.parentNode.remove();
    }
    if (e.target.className === 'saveAll-button') {
      e.target.parentNode.parentNode.getElementsByTagName('input').map(input => {
        input.disabled = true;
      });
    }
    if (e.target.id === 'location__addr') {
      $locaAddr = e.target;
      $locaName = e.target.previousElementSibling.previousElementSibling;
      show();
    }
  });

  let mapContainer = node.querySelector('.map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };

  let mainMap = new kakao.maps.Map(mainMapContainer, mapOption1); // 지도를 생성합니다

  setTimeout(() => {
    mainMap.relayout();
  }, 100);

  node.querySelector('.review-button').addEventListener('click', e => {
    if (e.target.textContent === '후기 작성 폼 열기') e.target.textContent = '후기 작성 폼 닫기';
    else e.target.textContent = '후기 작성 폼 열기';

    document.querySelector('.review-container').classList.toggle('hidden');
  });

  let positions2 = [
    {
      address_name: '배떡1',
      address: '서울 종로구 북촌로 35-4',
    },
    {
      address_name: '배떡2',
      address: '인천 부평구 신트리로8번길 9',
    },
    {
      address_name: '배떡3',
      address: '서울 관악구 봉천로 307',
    },
    {
      address_name: '배떡4',
      address: '울산 울주군 언양읍 웃방천1길 10',
    },
  ];

  // 마커를 담을 배열입니다
  let markers = [];
  let searchMapContainer = node.querySelector('#search-map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };
  // 지도를 생성합니다
  let searchMmap = new kakao.maps.Map(searchMapContainer, mapOption);

  setTimeout(() => {
    searchMmap.relayout();
  }, 100);

  // 장소 검색 객체를 생성합니다
  let ps = new kakao.maps.services.Places();
  // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
  let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
  // 키워드로 장소를 검색합니다
  searchPlaces();
  // 키워드 검색을 요청하는 함수입니다
  function searchPlaces() {
    let keyword = node.querySelector('#keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
      return false;
    }
    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch(keyword, placesSearchCB);
  }
  // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      // 정상적으로 검색이 완료됐으면
      // 검색 목록과 마커를 표출합니다
      displayPlaces(data);
      // 페이지 번호를 표출합니다
      displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      alert('검색 결과가 존재하지 않습니다.');
      return;
    } else if (status === kakao.maps.services.Status.ERROR) {
      alert('검색 결과 중 오류가 발생했습니다.');
      return;
    }
  }

  let placePosition;
  let marker;
  // 검색 결과 목록과 마커를 표출하는 함수입니다
  function displayPlaces(places) {
    let listEl = document.getElementById('placesList'),
      menuEl = document.getElementById('menu_wrap'),
      fragment = document.createDocumentFragment(),
      bounds = new kakao.maps.LatLngBounds(),
      listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);
    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (let i = 0; i < places.length; i++) {
      // 마커를 생성하고 지도에 표시합니다
      placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
      marker = addMarker(placePosition, i);
      let itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
      // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(placePosition);
      // 마커와 검색결과 항목에 mouseover 했을때
      // 해당 장소에 인포윈도우에 장소명을 표시합니다
      // mouseout 했을 때는 인포윈도우를 닫습니다
      (function (marker, title, address, road_address) {
        kakao.maps.event.addListener(marker, 'mouseover', function () {
          displayInfowindow(marker, title, address, road_address);
        });
        kakao.maps.event.addListener(marker, 'mouseout', function () {
          infowindow.close();
        });
        itemEl.onmouseover = function () {
          displayInfowindow(marker, title, address, road_address);
        };
        itemEl.onmouseout = function () {
          infowindow.close();
        };

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function () {
          // 마커 위에 인포윈도우를 표시합니다
          alert(road_address);
          document.getElementById('address').value = road_address;
          positions2.push({ address_name: '나나', address: road_address });
          positoins2Marker();
          //   positions2.push(JSON.parse(addPositions2));
          //positions2 배열 추가-->
          //   positions2.push({'나나',road_addresss});
        });
      })(marker, places[i].place_name, places[i].address_name, places[i].road_address_name);
      fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;
    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
    console.log(positions2);
  }

  // 검색결과 항목을 Element로 반환하는 함수입니다
  function getListItem(index, places) {
    let el = document.createElement('li'),
      itemStr =
        '<span class="markerbg marker_' +
        (index + 1) +
        '"></span>' +
        '<div class="info">' +
        '   <h5>' +
        places.place_name +
        '</h5>';
    if (places.road_address_name) {
      itemStr +=
        '    <span>' +
        places.road_address_name +
        '</span>' +
        '   <span class="jibun gray">' +
        places.address_name +
        '</span>';
    } else {
      itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' + '</div>';
    el.innerHTML = itemStr;
    el.className = 'item';
    return el;
  }
  // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
  function addMarker(position, idx, title) {
    let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
      imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
      imgOptions = {
        spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
        spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      },
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
      marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });
    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker); // 배열에 생성된 마커를 추가합니다
    return marker;
  }
  // 지도 위에 표시되고 있는 마커를 모두 제거합니다
  function removeMarker() {
    for (let i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }
  // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
  function displayPagination(pagination) {
    let paginationEl = document.getElementById('pagination'),
      fragment = document.createDocumentFragment(),
      i;
    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
      paginationEl.removeChild(paginationEl.lastChild);
    }
    for (i = 1; i <= pagination.last; i++) {
      let el = document.createElement('a');
      el.href = '#';
      el.innerHTML = i;
      if (i === pagination.current) {
        el.className = 'on';
      } else {
        el.onclick = (function (i) {
          return function () {
            pagination.gotoPage(i);
          };
        })(i);
      }
      fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
  }
  // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
  // 인포윈도우에 장소명을 표시합니다
  function displayInfowindow(marker, title, address, road_address) {
    let content =
      '<div style="padding:5px;z-index:1;">' +
      title +
      '<p class="title">지번 주소:</p>' +
      address +
      '<p class="title">도로명 주소:</p>' +
      road_address +
      '</div>';
    infowindow.setContent(content);
    infowindow.open(map, marker);
  }
  // 검색결과 목록의 자식 Element를 제거하는 함수입니다
  function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
      el.removeChild(el.lastChild);
    }
  }

  // let coords;
  // let marker2;
  // let markers2 = [];

  // function positions2Marker() {
  //   for (let i = 0; i < positions2.length; i++) {
  //     // 주소-좌표 변환 객체를 생성합니다
  //     let geocoder = new kakao.maps.services.Geocoder();
  //     let address = positions2[i]['address'];
  //     let address_name = positions2[i]['address_name'];

  //     // 주소로 좌표를 검색합니다
  //     geocoder.addressSearch(address, function (result, status) {
  //       // 정상적으로 검색이 완료됐으면
  //       if (status === kakao.maps.services.Status.OK) {
  //         coords = new kakao.maps.LatLng(result[0].y, result[0].x);

  //         // 마커를 생성합니다
  //         marker2 = new kakao.maps.Marker({
  //           map: map2, // 마커를 표시할 지도
  //           position: coords, // 마커의 위치
  //         });

  //         // 마커에 표시할 인포윈도우를 생성합니다
  //         let infowindow2 = new kakao.maps.InfoWindow({
  //           content: address_name, // 인포윈도우에 표시할 내용
  //         });

  //         // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
  //         // 이벤트 리스너로는 클로저를 만들어 등록합니다
  //         // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
  //         kakao.maps.event.addListener(marker2, 'mouseover', makeOverListener2(map2, marker2, infowindow2));
  //         kakao.maps.event.addListener(marker2, 'mouseout', makeOutListener2(infowindow2));
  //       }
  //     });
  //   }
  // }

  // // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
  // function makeOverListener2(map2, marker2, infowindow2) {
  //   return function () {
  //     infowindow2.open(map2, marker2);
  //   };
  // }

  // // 인포윈도우를 닫는 클로저를 만드는 함수입니다
  // function makeOutListener2(infowindow2) {
  //   return function () {
  //     infowindow2.close();
  //   };
  // }

  return node.children;
};

export default writeNode;
