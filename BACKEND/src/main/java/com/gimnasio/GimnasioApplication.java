package com.gimnasio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*Clave firestore
 * {
  "type": "service_account",
  "project_id": "gimnasioapp-401da",
  "private_key_id": "878552e09de3c69aef212fdebccaaee2f79d5e33",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCa1/YUc29scW0+\nZKnQViaC62UUZQT0N+tuAtszRh0bkgZScI+It8u5vCgU0UbS8L9tMknBjqJfJIE4\n4Lx/u3juNYBt6H5YGMGuQMDOFL6m8DIHVZzaQaQDj7HHyJs+tMvvFcOSGTLzYZO9\nV6mhwvTD9rzbefa7V0BerlLPfx2BBMpkRpCc1I/DC2xctTRI4MutWSDwTvPXe2rg\nxJdp5RXpRZeSAoGW9ArJVNXn3l7X39+q3fb9PMnXKeYjcNonGeSogSdIc9ZczDKW\nIZ7uq0fpzuyBZF/QZIxDImUyOLagB+x05w6WkLSWYkVKziV8AMlEMz8weZIJhc1Q\n/LXC8flbAgMBAAECggEAAhOpckbGFM9EptSUzn2JULI1jdt5AImLd9ND+2ESXN9N\nhGeMFUpZyijy5KyV5ZhjCOTmxljSZrJHpWqmPJRYH+HJ0i+PGkFiv6KQs3Llx7lr\nAlwsmWTN/Vo9CS/wnRuxrIg/MkNyQn9pit2VId1KiSJTECKHo6Wc8GgoJi5PR/6y\nGRM7N1ptyn4gpwTIWfrrfoY7jFREhT0+LSbWtcvO1AVFx4n9Gn5qFT0VohE99dUV\nriAOmJ8pc/MAlycPWFBAFZ2onN2d+xOmvkPjn1tstmuc5MmptNITl99/huME1AfD\nQClja5vhkMZkCZHeQnCoZmcLeMVjBYPDOnijKs3pOQKBgQDWwE/vxDAwrG3kHVMR\n60ByRyqj0LwEf/5W08ke4GMQLQDvga1QQhuezAI5OOYnbm1kT5MWG5BdsLXv9Pba\noEltJPzSauOsoiPxD8AK2zO08KCKFYpFy1h/xfP70V+RjT/CySCEMq5zBRKWt0eD\npwjLBVEVa1Y7HqRZyU8pAkU5DQKBgQC4leNSVRrdN9cnzfbZn/M250fONzfSEfN9\n8nmeNqUUo3vCVIzsdVA+qDtZp28IyrVRW155AJ2MJ3xAHIWAuntPEgIUo9SmLYNS\nJmEHVdsobLVtqblWv4EBulCpVsmAN5jmaqSWVPFZjKoFYJYeGgRFlPYBDNMEhnc5\nNsCV8ZSSBwKBgEz4zS4BQcyQYSr8H8Qyb/0F6RCiRmHOhpkUe+fnQVqQ5t7xue8k\nYsX+FWXxMP09JVRo+S1ZX9tU9UE2qiwee3L1aPD6gvRjgUJsBMdfXh8iiEg2BEOT\n6PqTNyoxF3ISEKJ+eHspt0FethTzSFy1x/8Mvs1Qla3HA91yvD7Uo38ZAoGBAKrj\nibt1sxNPWVAurPS50q/n22CdWn6Pw/+8dr/1E0xSNoepQ3a+/XWkxp2wF2nqUYdq\nscIQwcs52xx3y1lsTDDeGTMGoh7fUA7EHVxvLiD009sdIMuhLExvM7Iy+QZNcq8P\n2CSdq3wecGHeE9z2dTH+QOqj9tDftI0xlYa8S7INAoGAY2Bl1wdbplS1PvbyIGhr\n7ZCLcgyepAQhZwO73v1vpYhLDw2aXH8B3pNHRhuVNnc5oPcz8IDL7WGSW0ZZDAiS\nJi/+9hc5UxZwiUOjoV9gu7aFCvcPBzdGiWqACqJh83OYUP0Z6Kb+WO3gKiUOi9Wd\n9meuBvy7GjahwJ6bxxS+v+s=\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-fbsvc@gimnasioapp-401da.iam.gserviceaccount.com",
  "client_id": "110679178319087620092",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fbsvc%40gimnasioapp-401da.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}

 * 
 * */
@SpringBootApplication
public class GimnasioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimnasioApplication.class, args);
	}

}
