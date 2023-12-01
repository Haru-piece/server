# 같이도전

같이 도전할만한 챌린지를 공유하고, 챌린지를 완수하면 챌린지에 참여한 구성원들에게 그
결과를 공유합니다.  
구성원이 관심있어 할 만할 챌린지가 만들어졌을 경우 알림으로 알려줍니다.   
또, 특정 결과를 내면 뱃지를 수여하여 참여자의 의욕을 올려줍니다.

Client 포함 전체 코드: https://github.com/Haru-piece




## Tech Stack

**Client:** Kotlin

**Server:** Spring boot, Spring security, Spring Data JPA, WebSocket(STOMP messaging with Spring)


## Team

- 기획
- Client: [@임보성](https://www.github.com/brudenell)
- Server: [@김현규](https://www.github.com/kimm240), [@김기현](https://www.github.com/boogahead)

## Optimizations

- One To Many의 테이블을 DB에서 가져올 때 발생하는 JPA N + 1문제를 해결하기 위해, Fetch Join을 적용 

## 기타

 Slack, Jira, Postman으로 협업하였습니다.    
 Postman으로 서버가 잘 돌아가는지 테스트했습니다.    
 AWS EC2 서버로 백엔드 서버를 배포했습니다.
