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

-- "프리프롬", "알레르기", "대체식품"에 대한 예시 데이터 삽입
INSERT IGNORE INTO articles (title, date, author, tags, content, thumbnail_url, likes)
VALUES ('프리프롬 제품 트렌드 및 이해', CURRENT_TIMESTAMP, '정재훈', '프리프롬, 글루텐프리, 건강',
        '# 서론\n\n프리프롬 제품군은 특정 성분을 제외한 식품으로, 대표적으로 글루텐프리, 유제품프리, 설탕프리 제품이 있습니다. 이러한 제품들은 특정 알레르기를 가진 사람들뿐만 아니라 건강과 환경을 고려하는 소비자들에게도 각광받고 있습니다. 특히, 글루텐프리 제품은 셀리악병 환자들에게 필수적이며, 일반 소비자들 사이에서도 건강을 위해 널리 사용됩니다.\n\n# 본론\n\n프리프롬 제품의 트렌드는 글루텐 대체재로 사용되는 퀴노아, 타피오카, 아마란스와 같은 곡물의 인기와 더불어 확산되고 있습니다. 과거에는 이러한 제품들이 특정 질환을 가진 소비자들에 국한되었지만, 이제는 많은 소비자들이 건강하고 안전한 식품을 찾기 위해 프리프롬 제품을 선호하고 있습니다. 슈퍼마켓과 온라인 쇼핑몰에서도 다양한 프리프롬 제품을 쉽게 찾을 수 있게 되었으며, 이로 인해 접근성이 크게 향상되었습니다.\n\n또한, 기업들은 비건 인증과 같은 다양한 인증 제도를 도입하여 소비자들이 신뢰할 수 있는 제품을 선택할 수 있도록 돕고 있습니다. 건강을 고려한 소비뿐만 아니라 환경 보호에 대한 책임감도 이러한 프리프롬 제품의 인기를 더욱 높이는 요인 중 하나입니다. 프리프롬 제품의 시장은 앞으로도 성장할 것으로 기대되며, 건강한 라이프스타일을 추구하는 소비자들에게 중요한 선택지가 될 것입니다.\n\n# 결론\n\n프리프롬 제품은 단순히 특정 성분을 제외한 제품을 넘어, 소비자의 건강과 환경 보호를 고려한 선택으로 자리 잡고 있습니다. 앞으로도 다양한 프리프롬 제품이 개발되고 소비자들의 선택 폭은 넓어질 것입니다.',
        'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F3f3cc467-e467-4ad5-9e6b-8c20ff9018cc%2FKakaoTalk_20240409_093703410_13.jpg?table=block&id=5e377cbd-0998-4e2e-85ea-8197c43d28f7&cache=v2', 0),

       ('알레르기 관리 및 대체식품', CURRENT_TIMESTAMP, '박민수', '알레르기, 대체식품, 건강',
        '# 서론\n\n알레르기를 가진 소비자들은 일상 생활에서 다양한 제약을 경험합니다. 하지만, 최근 몇 년 동안 대체식품이 발전하면서 알레르기를 가진 사람들도 다양한 선택지를 가질 수 있게 되었습니다. 이들 대체식품은 알레르기를 유발하는 성분을 피하면서도 비슷한 영양소를 제공해 많은 이들에게 사랑받고 있습니다.\n\n# 본론\n\n대표적인 대체식품으로는 땅콩 알레르기가 있는 사람들을 위한 아몬드 버터, 해바라기씨 버터가 있습니다. 이들은 땅콩 버터와 비슷한 맛과 질감을 제공하면서도 땅콩이 유발할 수 있는 알레르기 반응을 피할 수 있습니다. 또한, 우유 알레르기나 유당불내증을 가진 사람들을 위한 식물성 대체 우유도 크게 인기를 끌고 있습니다. 아몬드 밀크, 오트 밀크, 코코넛 밀크 등 다양한 대체 우유는 영양가가 높고 환경에 미치는 영향도 적어 많은 이들이 선택하고 있습니다.\n\n최근에는 대체식품의 품질이 크게 개선되면서 맛과 영양을 모두 만족시킬 수 있는 제품들이 시장에 많이 등장하고 있습니다. 이는 알레르기를 가진 사람들뿐만 아니라, 건강을 중시하는 일반 소비자들에게도 매력적인 선택지가 되고 있습니다. 특히, 지속 가능한 생산과 환경 보호를 고려한 식품들이 소비자의 관심을 받고 있습니다.\n\n# 결론\n\n알레르기 대체식품은 단순한 대안에서 나아가, 건강한 식생활을 유지하는 중요한 자원이 되고 있습니다. 앞으로도 기술 발전을 통해 더욱 다양한 대체식품이 개발될 것이며, 소비자들은 보다 넓은 선택지를 가지게 될 것입니다.',
        'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F29830f17-360a-487c-9d78-90894086103d%2FKakaoTalk_20240409_094145927_01.jpg?table=block&id=656a484d-2078-46a5-8df4-7c9205a71ddf&cache=v2', 0),

       ('균형 잡힌 식단을 위한 대체식품', CURRENT_TIMESTAMP, '이정우', '대체식품, 식물성, 영양',
        '# 서론\n\n최근 몇 년 동안 건강과 환경에 대한 관심이 커지면서 대체식품에 대한 수요가 폭발적으로 증가하고 있습니다. 특히, 식물성 대체식품은 이러한 트렌드를 선도하고 있으며, 비건이나 채식주의자뿐만 아니라 일반 소비자들 사이에서도 널리 사용되고 있습니다.\n\n# 본론\n\n식물성 대체식품 중에서도 고기 대체품은 매우 인기가 높습니다. 콩, 두부, 곡물을 기반으로 한 대체 고기 제품은 동물성 고기와 유사한 질감과 맛을 제공하며, 단백질 함량이 높아 건강을 고려하는 소비자들에게 큰 호응을 얻고 있습니다. 또한, 식물성 대체 유제품도 아몬드 밀크, 코코넛 요거트 등의 형태로 시장에 많이 등장하고 있습니다. 이러한 제품들은 환경에 미치는 영향이 적고, 유제품 알레르기나 유당불내증을 가진 사람들에게 좋은 대안이 됩니다.\n\n특히, 대체식품은 건강을 중시하는 소비자들에게 단순한 대체재가 아닌, 필수적인 식품군으로 자리잡고 있습니다. 대체식품은 동물성 식품을 섭취하지 않아도 충분한 영양소를 제공할 수 있으며, 비타민 B12와 같은 필수 영양소를 첨가하는 등 제품의 품질과 영양소가 지속적으로 개선되고 있습니다.\n\n# 결론\n\n대체식품은 건강과 환경을 고려한 지속 가능한 선택으로 자리잡고 있으며, 앞으로도 더욱 많은 소비자들이 이를 선택할 것으로 예상됩니다. 새로운 기술과 재료를 바탕으로 한 혁신적인 대체식품이 계속 등장할 것이며, 이는 식품 산업의 중요한 흐름이 될 것입니다.',
        'https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fbafebf27-4e21-4ed6-99e1-983eb90ad9c0%2F061227b5-3df1-4cf1-a0e7-0dda0569bdff%2FKakaoTalk_20240409_093703410_14.jpg?table=block&id=18d1230c-d3bb-4cb0-a7b1-eea1b3a38d6b&cache=v2', 0);

-- products 테이블 초기 데이터 삽입
INSERT IGNORE INTO products (id, nutritional_info, product_image_url, meta_image_url, type_name, manufacturer, seller, capacity, product_name, ingredients, price, created_at, updated_at)
VALUES
    (1, '칼로리 200kcal, 탄수화물 30g, 단백질 5g', 'https://example.com/product1.jpg', 'https://example.com/meta1.jpg', '식품', '건강한 식품제조사', '헬시몰', '500ml', '헬시 글루텐 프리 주스', '물, 설탕, 향료', 12000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, '칼로리 150kcal, 지방 10g, 단백질 3g', 'https://example.com/product2.jpg', 'https://example.com/meta2.jpg', '식품', '자연우유', '푸드파이터', '250g', '락토 프리 요구르트', '락토 프리 우유, 천연 과일 농축액', 8000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '칼로리 100kcal, 탄수화물 20g, 단백질 2g', 'https://example.com/product3.jpg', 'https://example.com/meta3.jpg', '식품', '에코넛츠', '그린샵', '100g', '피넛 프리 땅콩버터 대체품', '아몬드, 소금', 9500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, '칼로리 50kcal, 탄수화물 10g', 'https://example.com/product4.jpg', 'https://example.com/meta4.jpg', '식품', '베지테리언키친', '비건마켓', '300ml', '비건 콩 단백 음료', '콩 추출물, 감미료', 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- allergy_categories 테이블 초기화 데이터와 매핑된 products_allergies 테이블 초기 데이터 삽입
INSERT IGNORE INTO products_allergies (product_id, allergy_id, created_at, updated_at)
VALUES
    (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 헬시 글루텐 프리 주스는 '알류' 알레르기를 가짐
    (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 락토 프리 요구르트는 '우유' 알레르기를 가짐
    (3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 피넛 프리 땅콩버터 대체품은 '땅콩' 알레르기를 가짐
    (4, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 비건 콩 단백 음료는 '대두' 알레르기를 가짐

-- free_from_categories 테이블 초기화 데이터와 매핑된 products_free_from 테이블 초기 데이터 삽입
INSERT IGNORE INTO products_free_from (product_id, free_from_id, created_at, updated_at)
VALUES
    (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 헬시 글루텐 프리 주스는 '글루텐 프리'
    (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 락토 프리 요구르트는 '락토 프리'
    (3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 피넛 프리 땅콩버터 대체품은 '피넛 프리'
    (4, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 비건 콩 단백 음료는 '비건'