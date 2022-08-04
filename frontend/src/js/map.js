let markers = [];
let mainMap = '';
let $locaAddr = '';
let $locaName = '';

const makeMap = (container, option) => {
  mainMap = new kakao.maps.Map(container, option);
  setTimeout(() => {
    mainMap.relayout();
  }, 100);
};

const setInputArea = (locaAddr, locaName) => {
  $locaAddr = locaAddr;
  $locaName = locaName;
};

function close() {
  document.querySelector('.background').className = 'background';
}

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

// 장소 검색 객체를 생성합니다
let ps = new kakao.maps.services.Places();

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
    (function (marker, addName, road_address_name) {
      kakao.maps.event.addListener(marker, 'mouseover', function () {
        displayInfowindow(marker, addName, road_address_name);
      });

      kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
      });

      itemEl.onmouseover = function () {
        displayInfowindow(marker, addName, road_address_name);
      };

      itemEl.onmouseout = function () {
        infowindow.close();
      };

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'click', function () {
        $locaAddr.value = road_address_name;
        $locaName.value = addName;
        close();
      });
      itemEl.onclick = function () {
        $locaAddr.value = road_address_name;
        $locaName.value = addName;
        close();
      };
    })(marker, places[i].place_name, places[i].road_address_name);

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

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}


const makedetailMap = (container, option) => {
  detailMap = new kakao.maps.Map(container, option);
  setTimeout(() => {
    mainMap.relayout();
  }, 100);
};


let marker2;

function detailpositionsMarker(positions) {
for (let i = 0; i < positions.length; i++) {

    // 주소-좌표 변환 객체를 생성합니다
    let geocoder = new kakao.maps.services.Geocoder();
    let no = positions[i]['no'];
    let address = positions[i]['addr'];
    let address_name=positions[i]['addr_name'];

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function(result, status) {

         // 정상적으로 검색이 완료됐으면
         if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 마커를 생성합니다
            marker2 = new kakao.maps.Marker({
                map: detailMap, // 마커를 표시할 지도
                position: coords // 마커의 위치
            });

            // 마커에 표시할 인포윈도우를 생성합니다
            let infowindow2 = new kakao.maps.InfoWindow({
                content: address_name // 인포윈도우에 표시할 내용
            });

            // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
            // 이벤트 리스너로는 클로저를 만들어 등록합니다
            // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
            kakao.maps.event.addListener(marker2, 'mouseover', makeOverListener(detailMap, marker2, infowindow2));
            kakao.maps.event.addListener(marker2, 'mouseout', makeOutListener(infowindow2));

         }
    });
  }
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(detailMap, marker2, infowindow2) {
    return function() {
        infowindow2.open(detailMap, marker2);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow2) {
    return function() {
        infowindow2.close();
    };
}

export default {
  searchPlaces,
  makeMap,
  close,
  setInputArea,
  makedetailMap,
  detailpositionsMarker
};
