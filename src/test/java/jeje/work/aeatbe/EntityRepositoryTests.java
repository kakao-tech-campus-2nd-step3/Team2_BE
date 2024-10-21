package jeje.work.aeatbe;

import jeje.work.aeatbe.dto.*;
import jeje.work.aeatbe.dto.User.UserDTO;
import jeje.work.aeatbe.entity.*;
import jeje.work.aeatbe.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

//dto, 엔티티 테스트
@DataJpaTest
public class EntityRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AllergyCategoryRepository allergyCategoryRepository;

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setAllergens("nuts");
        productDTO.setManufacturer("Test Manufacturer");

        Product product = new Product(
            productDTO.getId(),
            productDTO.getAllergens(),
            productDTO.getNutritionalInfo(),
            productDTO.getProductImageUrl(),
            productDTO.getMetaImageUrl(),
            productDTO.getTypeName(),
            productDTO.getManufacturer(),
            productDTO.getSeller(),
            productDTO.getCapacity(),
            productDTO.getProductName(),
            productDTO.getIngredients()
        );

        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

//    @Test
//    public void testCreateUser() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId("test_user");
//        userDTO.setAllergies("peanuts");
//
//        User user = new User(
//            userDTO.getId(),
//            userDTO.getUserId(),
//            userDTO.getAllergies(),
//            userDTO.getFreeFrom(),
//            "Test User",
//            "https://testImg.com"
//        );
//
//        User savedUser = userRepository.save(user);
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }

//    @Test
//    public void testCreateArticle() {
//        ArticleDTO articleDTO = new ArticleDTO();
//        articleDTO.setTitle("Test Article");
//        articleDTO.setAuthor("Author1");
//        articleDTO.setContent("test article");
//        articleDTO.setDate(new Timestamp(System.currentTimeMillis()));
//
//        Article article = new Article(
//            articleDTO.getId(),
//            articleDTO.getTitle(),
//            articleDTO.getDate(),
//            articleDTO.getAuthor(),
//            articleDTO.getTags(),
//            articleDTO.getContent(),
//            articleDTO.getThumbnailUrl(),
//            articleDTO.getLikes()
//        );
//
//        Article savedArticle = articleRepository.save(article);
//        assertThat(savedArticle.getId()).isGreaterThan(0);
//    }

//    @Test
//    public void testCreateWishlist() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId("test_user_wishlist");
//        User user = new User(
//            userDTO.getId(),
//            userDTO.getUserId(),
//            userDTO.getAllergies(),
//            userDTO.getFreeFrom(),
//            "Test User",
//            "https://testImg.com"
//        );
//        userRepository.save(user);
//
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductName("Test Product Wishlist");
//        Product product = new Product(
//            productDTO.getId(),
//            productDTO.getAllergens(),
//            productDTO.getNutritionalInfo(),
//            productDTO.getProductImageUrl(),
//            productDTO.getMetaImageUrl(),
//            productDTO.getTypeName(),
//            productDTO.getManufacturer(),
//            productDTO.getSeller(),
//            productDTO.getCapacity(),
//            productDTO.getProductName(),
//            productDTO.getIngredients()
//        );
//        productRepository.save(product);
//
//        WishlistDTO wishlistDTO = new WishlistDTO();
//        wishlistDTO.setUserId(user.getId());
//        wishlistDTO.setProductId(product.getId());
//
//        Wishlist wishlist = new Wishlist(
//            wishlistDTO.getId(),
//            user,
//            product
//        );
//
//        Wishlist savedWishlist = wishlistRepository.save(wishlist);
//        assertThat(savedWishlist.getId()).isGreaterThan(0);
//    }

    @Test
    public void testCreateAllergyCategory() {
        AllergyCategoryDTO categoryDTO = new AllergyCategoryDTO();
        categoryDTO.setAllergyType("Gluten");

        AllergyCategory category = new AllergyCategory(
            categoryDTO.getId(),
            categoryDTO.getAllergyType()
        );

        AllergyCategory savedCategory = allergyCategoryRepository.save(category);
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

}