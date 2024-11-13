INSERT IGNORE INTO allergy_categories (allergy_type)
VALUES ('난류'),
       ('우유'),
       ('메밀'),
       ('땅콩'),
       ('대두'),
       ('밀'),
       ('잣'),
       ('호두'),
       ('게'),
       ('새우'),
       ('오징어'),
       ('고등어'),
       ('조개류'),
       ('복숭아'),
       ('토마토'),
       ('닭고기'),
       ('돼지고기'),
       ('쇠고기'),
       ('아황산류'),
       ('홍합'),
       ('전복'),
       ('굴'),
       ('콩류'),
       ('아몬드');

INSERT IGNORE INTO free_from_categories (free_from_type)
VALUES ('글루텐 프리'),
       ('락토 프리'),
       ('피넛 프리'),
       ('나트륨 프리'),
       ('비건'),
       ('GMO 프리'),
       ('콜리스테롤 프리'),
       ('슈가 프리'),
       ('디카페인'),
       ('TEST');

INSERT IGNORE INTO articles (title, date, author, tags, content, thumbnail_url, likes)
VALUES
    ('지속 가능한 식생활: 환경을 고려한 선택', '2024-09-12', '서연희', '지속 가능성, 환경, 건강',
     '# 서론\n\n지속 가능한 식생활은 환경과 건강을 동시에 고려한 식사 습관입니다.\n\n# 본론\n\n지속 가능한 식생활을 실천하기 위한 다양한 방법을 알아봅니다.\n\n# 결론\n\n환경과 건강을 위한 식습관을 꾸준히 실천하는 것이 중요합니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F0ee11763-6810-4e7e-b48a-9869039d6d02%2Fimage_19.jpg?table=block&id=13ba63f1-9420-8048-8240-ddb1efaaaa41&cache=v2', 0),

    ('고혈압 관리를 위한 생활 습관', '2024-08-30', '박민수', '고혈압, 건강, 생활습관',
     '# 서론\n\n고혈압은 성인 건강 문제 중 하나로, 생활 습관이 중요합니다.\n\n# 본론\n\n고혈압을 예방하고 관리하기 위한 올바른 생활 습관을 소개합니다.\n\n# 결론\n\n건강한 생활 습관이 고혈압 예방에 도움이 됩니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Fc31e7682-623a-4418-8d87-8a37d798a909%2Fimage_20.jpg?table=block&id=13ba63f1-9420-80d3-8fa5-e93a97e19d16&cache=v2', 0),

    ('알레르기 예방을 위한 식단 가이드', '2024-07-05', '김하나', '알레르기, 예방, 건강',
     '# 서론\n\n알레르기 반응을 줄이기 위한 올바른 식단 관리가 중요합니다.\n\n# 본론\n\n알레르기 예방을 위한 다양한 식단 전략을 소개합니다.\n\n# 결론\n\n올바른 식단이 알레르기 예방에 효과적일 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F5e46425e-ebc2-4735-ab16-45a36bd9e5ee%2Fimage_22.jpg?table=block&id=13ba63f1-9420-807e-90e8-d050d8f753fa&cache=v2', 0),

    ('식물성 오일의 종류와 건강 효과', '2024-06-22', '이정우', '식물성 오일, 건강, 영양',
     '# 서론\n\n식물성 오일은 건강에 다양한 이점을 제공하는 식품입니다.\n\n# 본론\n\n다양한 식물성 오일의 특성과 건강에 미치는 영향을 소개합니다.\n\n# 결론\n\n식물성 오일을 통해 건강한 영양을 섭취할 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Fabf32e65-4a58-4e9f-b09b-c7bb59fae421%2Fimage_23.jpg?table=block&id=13ba63f1-9420-8007-a4e7-ec996c09145a&cache=v2', 0),

    ('당뇨 예방을 위한 식이요법', '2024-05-18', '김윤지', '당뇨, 예방, 건강',
     '# 서론\n\n당뇨 예방을 위해서는 올바른 식이요법이 중요합니다.\n\n# 본론\n\n당뇨 예방에 효과적인 식이요법과 주의할 점을 알아봅니다.\n\n# 결론\n\n올바른 식이요법이 당뇨 예방에 큰 도움이 됩니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Faeaab96a-4c30-4de5-93d0-28eb9231192e%2Fimage_24.jpg?table=block&id=13ba63f1-9420-802a-9d71-f800ae9e6027&cache=v2', 0),

    ('대체 단백질의 미래', '2024-04-29', '정재훈', '대체 단백질, 건강, 환경',
     '# 서론\n\n대체 단백질은 환경과 건강을 위한 새로운 식품 선택지입니다.\n\n# 본론\n\n대체 단백질의 종류와 미래 전망을 알아봅니다.\n\n# 결론\n\n대체 단백질은 지속 가능한 미래의 식품입니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Ff64aeafa-17f5-412e-9fac-2b1295b6bd67%2Fimage_25.jpg?table=block&id=13ba63f1-9420-80e3-b479-ed377223b142&cache=v2', 0),

    ('간 건강을 위한 영양소', '2024-04-02', '박민수', '간 건강, 영양소, 건강',
     '# 서론\n\n간 건강을 유지하기 위해서는 특정 영양소가 필요합니다.\n\n# 본론\n\n간 건강을 위해 섭취해야 할 주요 영양소를 소개합니다.\n\n# 결론\n\n간 건강을 위해 필요한 영양소를 적절히 섭취해야 합니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F0a451e30-0bdf-47bc-a828-d71a87749483%2Fimage_26.jpg?table=block&id=13ba63f1-9420-8060-a29c-c368583bef88&cache=v2', 0),

    ('심리적 안정감을 위한 식사법', '2024-03-25', '김하나', '심리, 식사, 건강',
     '# 서론\n\n심리적 안정감을 위해 식사 방법이 큰 영향을 미칠 수 있습니다.\n\n# 본론\n\n심리적 안정에 도움이 되는 식사법을 소개합니다.\n\n# 결론\n\n심리적 건강을 위해 균형 잡힌 식사를 실천합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Ffd0d3d5c-afa1-4572-87db-037c664bf637%2Fimage_27.jpg?table=block&id=13ba63f1-9420-80ec-88ac-f7ec2e9b9ec9&cache=v2', 0),

    ('칼슘 섭취의 중요성', '2024-02-14', '이정우', '칼슘, 건강, 영양소',
     '# 서론\n\n칼슘은 뼈 건강과 신체 기능에 중요한 역할을 합니다.\n\n# 본론\n\n칼슘의 역할과 효과적인 섭취 방법을 알아봅니다.\n\n# 결론\n\n칼슘을 충분히 섭취하여 건강을 유지할 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F4f8b2bd2-9492-4ec0-81db-abb55db97d40%2Fimage_28.jpg?table=block&id=13ba63f1-9420-80a4-9334-f38b1a880bc2&cache=v2', 0),

    ('비건 단백질 보충제의 선택 방법', '2024-01-10', '서연희', '비건, 단백질, 건강',
     '# 서론\n\n비건 단백질 보충제는 비건 라이프스타일에 중요한 요소입니다.\n\n# 본론\n\n비건 단백질 보충제를 선택할 때 고려해야 할 사항을 알아봅니다.\n\n# 결론\n\n올바른 단백질 보충제를 선택하여 건강을 유지할 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F34468fc3-1d1a-48e0-9160-66ebb4d5b5bf%2Fimage_29.jpg?table=block&id=13ba63f1-9420-801d-a514-ff5524b0ffaf&cache=v2', 0),

    ('정신 건강을 위한 영양소', '2023-12-03', '김윤지', '정신 건강, 영양소, 건강',
     '# 서론\n\n정신 건강은 영양소와 깊은 연관이 있습니다.\n\n# 본론\n\n정신 건강에 도움이 되는 주요 영양소를 알아봅니다.\n\n# 결론\n\n적절한 영양 섭취는 정신 건강 유지에 필수적입니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F30b36b58-912f-435a-8a8e-6673badcde18%2Fimage_30.jpg?table=block&id=13ba63f1-9420-8097-995f-cb98b799d70d&cache=v2', 0),

    ('식물성 식품으로 풍부한 단백질 섭취', '2023-11-25', '박민수', '식물성 단백질, 건강',
     '# 서론\n\n식물성 단백질은 환경 친화적이면서도 영양가가 높습니다.\n\n# 본론\n\n식물성 식품으로 단백질을 효과적으로 섭취하는 방법을 알아봅니다.\n\n# 결론\n\n식물성 단백질을 통해 충분한 영양소를 섭취할 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F9c0cd461-d0af-4ea0-a8f3-36a147ca2b93%2Fimage_31.jpg?table=block&id=13ba63f1-9420-80e5-b7df-dd7d9981b601&cache=v2', 0),

    ('식물성 단백질의 미래', '2023-10-10', '김하나', '식물성 단백질, 건강',
     '# 서론\n\n식물성 단백질은 환경을 위한 지속 가능한 선택으로 주목받고 있습니다.\n\n# 본론\n\n식물성 단백질의 미래와 지속 가능성을 논의합니다.\n\n# 결론\n\n식물성 단백질은 건강과 환경을 모두 고려한 식단 선택입니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F292fec82-5f36-4867-a5ab-484d276eb98c%2Fimage_32.jpg?table=block&id=13ba63f1-9420-8019-81f0-dc2acf217b1a&cache=v2', 0),

    ('임산부를 위한 영양소 가이드', '2023-09-15', '이정우', '임산부, 영양소, 건강',
     '# 서론\n\n임산부에게 필요한 영양소는 태아와 산모 건강에 매우 중요합니다.\n\n# 본론\n\n임산부에게 중요한 영양소와 섭취 방법을 알아봅니다.\n\n# 결론\n\n임산부와 태아의 건강을 위해 필수 영양소를 섭취합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Fe9b6d277-564b-4f4b-9a9b-ab88c0473e66%2Fimage_33.jpg?table=block&id=13ba63f1-9420-80c2-bb8b-c3af6c8cc641&cache=v2', 0),

    ('어린이 식단에서 피해야 할 음식들', '2023-08-20', '서연희', '어린이, 식단, 건강',
     '# 서론\n\n어린이의 성장과 건강을 위해 식단 관리가 필수적입니다.\n\n# 본론\n\n어린이에게 피해야 할 음식 종류와 이유를 알아봅니다.\n\n# 결론\n\n건강한 성장과 발달을 위해 올바른 식단을 선택합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F8bfa7b9d-1f46-405a-8cdb-0901d08855b8%2Fimage_34.jpg?table=block&id=13ba63f1-9420-8010-954d-f934658441a7&cache=v2', 0),

    ('비타민 D의 역할과 결핍 예방', '2023-07-01', '박민수', '비타민 D, 건강, 결핍 예방',
     '# 서론\n\n비타민 D는 뼈와 면역력에 중요한 영양소입니다.\n\n# 본론\n\n비타민 D의 역할과 결핍을 예방하는 방법을 알아봅니다.\n\n# 결론\n\n비타민 D를 적절히 섭취하여 건강을 유지할 수 있습니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F4a238c54-df07-4b48-a55e-08f23a6c094e%2Fimage_35.jpg?table=block&id=13ba63f1-9420-809c-be3d-ccac4b07b9a0&cache=v2.jpg', 0),

    ('대사 건강을 위한 건강한 식단', '2023-06-14', '김윤지', '대사 건강, 식단, 건강',
     '# 서론\n\n대사 건강은 식단에 큰 영향을 받습니다.\n\n# 본론\n\n대사 건강에 도움이 되는 식단 구성 방법을 알아봅니다.\n\n# 결론\n\n대사 건강을 위해 올바른 식습관을 실천합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F0ee11763-6810-4e7e-b48a-9869039d6d02%2Fimage_19.jpg?table=block&id=13ba63f1-9420-8048-8240-ddb1efaaaa41&cache=v2', 0),

    ('체중 감량을 위한 식단 전략', '2023-05-28', '김하나', '체중 감량, 식단, 건강',
     '# 서론\n\n체중 감량을 위해서는 균형 잡힌 식단이 필요합니다.\n\n# 본론\n\n체중 감량에 효과적인 식단 전략을 소개합니다.\n\n# 결론\n\n건강한 체중 감량을 위해 균형 잡힌 식단을 유지합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F5e46425e-ebc2-4735-ab16-45a36bd9e5ee%2Fimage_22.jpg?table=block&id=13ba63f1-9420-807e-90e8-d050d8f753fa&cache=v2', 0),

    ('여름철 수분 섭취의 중요성', '2023-04-10', '이정우', '수분, 건강, 여름철',
     '# 서론\n\n여름철에는 수분 섭취가 특히 중요합니다.\n\n# 본론\n\n수분 섭취의 중요성과 효과적인 방법을 알아봅니다.\n\n# 결론\n\n여름철 건강을 위해 충분한 수분을 섭취합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2Fabf32e65-4a58-4e9f-b09b-c7bb59fae421%2Fimage_23.jpg?table=block&id=13ba63f1-9420-8007-a4e7-ec996c09145a&cache=v2', 0),

    ('천연 감기 예방법', '2023-03-22', '박민수', '감기, 천연 예방, 건강',
     '# 서론\n\n감기 예방을 위한 천연 방법이 있습니다.\n\n# 본론\n\n감기 예방에 효과적인 천연 방법을 소개합니다.\n\n# 결론\n\n천연 방법으로 감기를 예방하고 건강을 지킵시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F32713af1-1163-4d59-9d6a-7b56fa689211%2Fimage_16.jpg?table=block&id=13ba63f1-9420-80c8-9356-d8d9a8afb979&cache=v2', 0),

    ('암 예방을 위한 건강한 식단', '2023-02-15', '서연희', '암 예방, 식단, 건강',
     '# 서론\n\n암 예방을 위한 식단 관리가 중요합니다.\n\n# 본론\n\n암 예방에 도움이 되는 건강한 식단을 소개합니다.\n\n# 결론\n\n건강한 식단을 통해 암 예방을 실천합시다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F8dcb536d-9a27-4dec-97e4-c2dd2512e6b1%2Fimage_9.jpg?table=block&id=13ba63f1-9420-8083-98aa-f848b4751ace&cache=v2', 0),

    ('식물성 대체육의 발전과 미래', '2023-01-05', '김윤지', '대체육, 식물성, 건강',
     '# 서론\n\n식물성 대체육은 지속 가능한 식품으로 주목받고 있습니다.\n\n# 본론\n\n대체육의 발전 과정과 미래 전망을 알아봅니다.\n\n# 결론\n\n지속 가능한 식생활을 위해 대체육의 발전이 중요합니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F1b808298-0fdb-49b1-b4a7-af3eb07fa9dd%2Fimage_4.jpg?table=block&id=13ba63f1-9420-807d-9c35-dac3f0923bda&cache=v2', 0),

    ('심혈관 건강을 위한 영양소', '2022-12-20', '김하나', '심혈관, 영양소, 건강',
     '# 서론\n\n심혈관 건강을 위해서는 특정 영양소가 필요합니다.\n\n# 본론\n\n심혈관 건강에 도움이 되는 주요 영양소를 소개합니다.\n\n# 결론\n\n심혈관 건강을 위해 영양소를 적절히 섭취해야 합니다.',
     'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F32713af1-1163-4d59-9d6a-7b56fa689211%2Fimage_16.jpg?table=block&id=13ba63f1-9420-80c8-9356-d8d9a8afb979&cache=v2', 0),

    ('노화 방지를 위한 항산화 식품', '2022-11-12', '이정우', '노화 방지, 항산화, 건강',
     '# 서론\n\n항산화 식품은 노화 방지에 도움을 줍니다.\n\n# 본론\n\n항산화 식품의 종류와 효과를 알아봅니다.\n\n# 결론\n\n항산화 식품을 통해 건강한 노화를 유지할 수 있습니다.',
     'https://img.notionusercontent.com/s3/prod-files-secure%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F501815fb-2f91-46db-a7f9-2e7c302f64d5%2Fimage_5.jpg/size/?exp=1731392590&sig=cBODhM7CCLr1omaNd-GDdoRl68PTVRZUgBlunnGr2Vs', 0);


---- products 테이블 초기 데이터 삽입
--INSERT IGNORE INTO products (id, nutritional_info, product_image_url, meta_image_url, type_name, manufacturer, seller,
--                             capacity, product_name, ingredients, price, created_at, updated_at)
--VALUES (1, '칼로리 200kcal, 탄수화물 30g, 단백질 5g', 'https://example.com/product1.jpg', 'https://example.com/meta1.jpg', '식품',
--        '건강한 식품제조사', '헬시몰', '500ml', '헬시 글루텐 프리 주스', '물, 설탕, 향료', 12000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--       (2, '칼로리 150kcal, 지방 10g, 단백질 3g', 'https://example.com/product2.jpg', 'https://example.com/meta2.jpg', '식품',
--        '자연우유', '푸드파이터', '250g', '락토 프리 요구르트', '락토 프리 우유, 천연 과일 농축액', 8000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--       (3, '칼로리 100kcal, 탄수화물 20g, 단백질 2g', 'https://example.com/product3.jpg', 'https://example.com/meta3.jpg', '식품',
--        '에코넛츠', '그린샵', '100g', '피넛 프리 땅콩버터 대체품', '아몬드, 소금', 9500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--       (4, '칼로리 50kcal, 탄수화물 10g', 'https://example.com/product4.jpg', 'https://example.com/meta4.jpg', '식품', '베지테리언키친',
--        '비건마켓', '300ml', '비건 콩 단백 음료', '콩 추출물, 감미료', 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
---- allergy_categories 테이블 초기화 데이터와 매핑된 products_allergies 테이블 초기 데이터 삽입
--INSERT IGNORE INTO products_allergies (product_id, allergy_id, created_at, updated_at)
--VALUES (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 헬시 글루텐 프리 주스는 '알류' 알레르기를 가짐
--       (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 락토 프리 요구르트는 '우유' 알레르기를 가짐
--       (3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 피넛 프리 땅콩버터 대체품은 '땅콩' 알레르기를 가짐
--       (4, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
---- 비건 콩 단백 음료는 '대두' 알레르기를 가짐
--
---- free_from_categories 테이블 초기화 데이터와 매핑된 products_free_from 테이블 초기 데이터 삽입
--INSERT IGNORE INTO products_free_from (product_id, free_from_id, created_at, updated_at)
--VALUES (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 헬시 글루텐 프리 주스는 '글루텐 프리'
--       (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 락토 프리 요구르트는 '락토 프리'
--       (3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 피넛 프리 땅콩버터 대체품은 '피넛 프리'
--       (4, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 비건 콩 단백 음료는 '비건'