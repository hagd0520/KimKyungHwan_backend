# 좋은 과제를 풀 기회를 주셔서 감사합니다. 즐거운 하루 되세요!

# 실행 방법

> docker compose up -d
 
위 명령어를 실행합니다.

도커 컴포즈 실행 후 스프링 애플리케이션이 8081 포트에 할당됩니다.

`http://127.0.0.1:8081/swagger-ui/index.html` 경로를 통해 스웨거 화면에 진입할 수 있습니다.

# 주요 구현 사항

- 과제 설명서에 명시된 API
- Redis 기반 Unique 계좌번호 생성 기능 (AccountNumberGenerator)
- 비관 락 기반 사용자별, 거래 유형별 일일 이체 한도 관리 기능 (AccountTransferDailyLimit)
- 비관 락 기반 계좌 잔액 관리 기능
- 거래 유형별 수수료 정책 관리 기능

# API 명세

## Account (계좌 관리)

### `POST /api/v1/accounts`
- 새로운 계좌를 생성합니다.

### `DELETE /api/v1/accounts`
- 계좌를 삭제합니다. soft delete 방식으로 삭제됩니다. 

### `PUT /api/v1/accounts/{accountId}/transfer-daily-limit`
- 특정 계좌의 일일 이체 한도를 변경합니다.

## Transfer (이체 관리)

### `POST /api/v1/transfers/deposit`
- 특정 계좌로 입금합니다. 무통장 입금이며, 요청값에 입금자명이 필수로 포함됩니다.

### `POST /api/v1/transfers/withdraw`
- 특정 계좌에서 출금합니다. 요청값에 출금자명이 필수로 포함됩니다.

### `POST /api/v1/transfers/account-transfer`
- 특정 계좌에서 다른 계좌로 이체합니다.

### `GET /api/v1/transfers`
- 특정 계좌의 이체 내역을 조회합니다.

### `PUT /api/v1/transfers/fee-options`
- 이체 수수료 정책을 변경합니다.