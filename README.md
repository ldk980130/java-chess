# java-controller
## 기능 요구사항

### 1단계

- 콘솔 UI에서 체스 게임을 할 수 있는 기능을 구현한다.
- 1단계는 체스 게임을 할 수 있는 체스판을 초기화한다.
- 체스판에서 말의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.

```plaintext
RNBQKBNR  8 (rank 8)
PPPPPPPP  7
........  6
........  5
........  4
........  3
pppppppp  2
rnbqkbnr  1 (rank 1)

abcdefgh
```

- 체스판에서 각 진영은 검은색(대문자)과 흰색(소문자) 편으로 구분한다.

#### 프로그램 실행 결과

```plaintext
체스 게임을 시작합니다.
게임 시작은 start, 종료는 end 명령을 입력하세요.
start
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr

end
```

### 2단계

- 콘솔 UI에서 체스 게임을 할 수 있는 기능을 구현한다.
- 체스 말의 이동 규칙을 찾아보고 체스 말이 이동할 수 있도록 구현한다.
- `move source위치 target위치`을 실행해 이동한다.
- 체스판의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.

#### 프로그램 실행 결과

```plaintext
> 체스 게임을 시작합니다.
> 게임 시작 : start
> 게임 종료 : end
> 게임 이동 : move source위치 target위치 - 예. move b2 b3
start
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr

move b2 b3
RNBQKBNR
PPPPPPPP
........
........
........
.p......
p.pppppp
rnbqkbnr
```

### 3단계

- 체스 게임은 상대편 King이 잡히는 경우 게임에서 진다. **King이 잡혔을 때 게임을 종료해야 한다.**
- **체스 게임은 현재 남아 있는 말에 대한 점수를 구할 수 있어야 한다.**
- "status" 명령을 입력하면 각 진영의 점수를 출력하고 어느 진영이 이겼는지 결과를 볼 수 있어야 한다.

#### 점수 계산 규칙

- 체스 프로그램에서 현재까지 남아 있는 말에 따라 점수를 계산할 수 있어야 한다.
- 각 말의 점수는 queen은 9점, rook은 5점, bishop은 3점, knight는 2.5점이다.
- pawn의 기본 점수는 1점이다. 하지만 같은 세로줄에 같은 색의 폰이 있는 경우 1점이 아닌 0.5점을 준다.
- king은 잡히는 경우 경기가 끝나기 때문에 점수가 없다.
- 한 번에 한 쪽의 점수만을 계산해야 한다.

```plaintext
.KR.....  8
P.PB....  7
.P..Q...  6
........  5
.....nq.  4
.....p.p  3
.....pp.  2
....rk..  1

abcdefgh
```

- 위와 같은 체스 판의 점수 결과는 검은색(대문자)은 20점. 흰색(소문자)은 19.5점이 된다.
- 검은색은 5(rook) + 3(bishop) + 9(queen) + 3(pawn) + 0(king) = 20점
- 흰색은 5(rook) + 2.5(knight) + 9(queen) + 3(pawn, 4개의 pawn이 있지만 세로로 있어 각 0.5이 된다.) + 0(king) = 19.5점

## 기능

### 1단계 

좌표를 표현
 -  Row
 -  Column

부모 Class - Chess

- chesspiece
  - 종류별로 생성

부모 Class - Gamer

- has a Chess

- 흑팀, 백팀
  - chesspiece를 가짐

gameBorad

- 게임판
  - 초기화(8 x 8)

startAndEnd

- Start && End 선택



