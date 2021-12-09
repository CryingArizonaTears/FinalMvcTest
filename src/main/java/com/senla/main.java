package com.senla;

import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.*;
import com.senla.config.SpringConfig;
import com.senla.controllers.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        IUserService userProfileService = context.getBean(IUserService.class);
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("qwe");
//        userLoginDto.setPassword("1234");
//        userProfileService.registration(userLoginDto);
        UserController userController = context.getBean(UserController.class);

        IAdService adService = context.getBean(IAdService.class);
        ICommentService commentService = context.getBean(ICommentService.class);
//        commentService.createComment(1L, 1L, "test1");
//        adService.getCurrentAds().forEach(System.out::println);


        IChatAndMessageService chatAndMessageService = context.getBean(IChatAndMessageService.class);
//        userProfileService.editProfileAsAdmin(1L, "editTest", "12345", "editTest");
//        userProfileService.registration("test1", "1234", "testik1");
//        userProfileService.registration("test2", "1234", "testik2");
//        userProfileService.registration("test3", "1234", "testik3");
//        chatAndMessageService.sendMessage(1L, 1L, "test1");
//        UserProfileDao userProfileDao = context.getBean(UserProfileDao.class);
//        chatAndMessageService.createChat(1L, 3L);
//        IAdService adService = context.getBean(IAdService.class);
//        ICategoryService categoryService = context.getBean(ICategoryService.class);
//        categoryService.createCategory("testCategory1");
//        adService.createAd("testAd1", 1L, "testAd1", 3D, 1L);
//        adService.editAd(1L, "editTest", 1L, "editTest", 3D);
//        adService.deleteAd(1L);
//        IAdDao adDao = new AdDao();
//        System.out.println(adDao.getCurrentAds());

        IUserProfileDao userProfileDao = context.getBean(IUserProfileDao.class);
//        userProfileDao.getAll().forEach(System.out::println);

//        ICommentService commentService = context.getBean(ICommentService.class);
        IMaintenanceService maintenanceService = context.getBean(IMaintenanceService.class);
//        maintenanceService.createMaintenance("test1", "test1", 1D, 3);
//        maintenanceService.addMaintenanceToAd(1L, 1L);
//        chatAndMessageService.createChat(2L, 3L);
//        adService.createAd("testik4", 1L, "testik4", 4D, 2L);
//        System.out.println(adService.getById(1L));
//        System.out.println(adService.getById(2L));
//        adService.createAd("test3", 1L, "test3", 1D, 1L);
//        adService.createAd("test2", 1L, "test2", 2D, 2L);
//        commentService.createComment(1L, 1L, "test");
//        adService.deleteAd(3L);
//        adService.filterClosedByUserId(1L).forEach(System.out::println);
//        System.out.println(adService.getCurrentAds());
//        adService.getById(1L);
//        adService.getCurrentAds().forEach(System.out::println);
//        System.out.println(adService.getCurrentAds());
//        chatAndMessageService.sendMessage(2L,2L, "testMessage2");
        IRatingService ratingService = context.getBean(IRatingService.class);
//        ratingService.addMarkToUser(2L, 1L, 1);
//        System.out.println(userProfileDao.get(1L));
//        System.out.println(userProfileDao.get(2L));
//        System.out.println(userProfileDao.get(3L));
    }
}
