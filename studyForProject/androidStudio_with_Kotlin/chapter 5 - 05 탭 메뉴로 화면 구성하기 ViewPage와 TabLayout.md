# chapter 5 - 05 탭 메뉴로 화면 구성하기 : ViewPager와 TabLayout

- ViewPager

    프래그먼트나 뷰를 화면을 넘기면서 사용할 수 있게 도와줌.

    1. 원하는 프래그먼트들 생성
    2. 어댑터 생성 (FragmentStateAdapter 상속) 먼저 fragmentList를 어댑터 클래스 안에 선언한다.
    3. getCount에는 fragmenList의 size를 리턴, getItem은 fragment[position]을 리턴한다.
    4. 매인 액티비티에서 이를 적용한다. 프래그먼트들의 리스트를 정의한다.
    5. 어댑터의 프래그먼트 리스트를 우리가 사용할 프래그먼트들의 리스트들로 정의한다.
    6. ViewPager의 어댑터를 우리가 정의한 어댑터로 선언한다.

- TabLayout

    우리 모두 아는 탭 레이아웃이다. 아래에서 프래그먼트나 뷰의 화면 전환을 도와주고, 시각화해준다.

    1. 우리가 사용할 tabtitles를 뮤터블 리스트 (listof)로 지정한다.
    2. TabLayoutMediator를 이용해 Viewpager와 TabLayout을 연결한다.