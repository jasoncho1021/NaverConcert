package kr.or.connect.resv.dao;

public class ReservationDaoSql {

    public static final String SELECT_RESERVATION_COMMENT_IMAGE_BY_COMMENT_ID = "select " +
            "fi.content_type, " +
            "fi.create_date, " +
            "fi.modify_date, " +
            "fi.delete_flag, " +
            "ci.file_id, " +
            "fi.file_name, " +
            "ci.id as image_id, " +
            "ci.reservation_info_id, " +
            "ci.reservation_user_comment_id, " +
            "fi.save_file_name " +
            "from " +
            "reservation_user_comment_image ci " +
            "join " +
            "file_info fi " +
            "on " +
            "fi.id = ci.file_id and ci.reservation_user_comment_id = :commentId";
    public static final String SELECT_RESERVATION_COMMENT_BY_COMMENT_ID = "select " +
            "comment, " +
            "id as comment_id, " +
            "create_date, " +
            "modify_date, " +
            "product_id, " +
            "reservation_info_id, " +
            "score " +
            "from " +
            "reservation_user_comment " +
            "where " +
            "id = :commentId";
    public static final String UPDATE_RESERVAION_INFO_CANCEL = "update reservation_info set cancel_flag = 1, modify_date = now() where id = :reservationInfoId";
    public static final String SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID = "select " +
            "cancel_flag as cancel_yn, " +
            "create_date, " +
            "display_info_id, " +
            "modify_date, " +
            "product_id, " +
            "reservation_date, " +
            "reservation_email, " +
            "id as reservation_info_id, " +
            "reservation_name, " +
            "reservation_tel as reservation_telephone " +
            "from " +
            "reservation_info " +
            "where " +
            "id = :reservationInfoId";
    public static final String SELECT_RESERVATION_PRICE_BY_RESERVATION_INFO_ID =
            "select count, product_price_id, id as reservation_info_price_id, reservation_info_id " +
                    "from " +
                    "reservation_info_price " +
                    "where " +
                    "reservation_info_id = :reservationInfoId";
    public static final String SELECT_NESTED_RESERVATION_INFO_BY_RESERVATION_EMAIL = "select rdi.reservation_info_id as \"reservation_info_id\",  rdi.reservation_name as \"reservation_name\", rdi.reservation_tel as \"reservation_telephone\", rdi.reservation_email as \"reservation_email\", rdi.reservation_date as \"reservation_date\", rdi.cancel_flag as \"cancel_yn\", rdi.reservation_info_create_date as \"create_date\",  rdi.reservation_info_modify_date as \"modify_date\", rdi.display_info_id as \"display_info_id\", rdi.product_id as \"product_id\", rdi.total_price as \"total_price\", rdi.display_info_id as \"display_info.display_info_id\", rdi.product_id as \"display_info.product_id\", rdi.opening_hours as \"display_info.opening_hours\", rdi.place_name as \"display_info.place_name\", rdi.place_lot as \"display_info.place_lot\", rdi.place_street as \"display_info.place_street\", rdi.tel as \"display_info.telephone\", rdi.homepage as \"display_info.homepage\", rdi.email as \"display_info.email\", rdi.display_info_create_date as \"display_info.create_date\", rdi.display_info_modify_date as \"display_info.modify_date\", p.content as \"display_info.product_content\", p.description as \"display_info.product_description\", p.event as \"display_info.product_event\", p.category_id as \"display_info.category_id\", c.name as \"display_info.category_name\" from  (  select  display_info_id, x.product_id, opening_hours, place_name, place_lot, place_street, tel, homepage, email, display_info_create_date, display_info_modify_date, x.reservation_info_id, reservation_name, reservation_tel, reservation_email, reservation_date, cancel_flag, reservation_info_create_date, reservation_info_modify_date, sum(pp.price * rip.count) as total_price from ( select  di.id as display_info_id, di.product_id, di.opening_hours, di.place_name, di.place_lot, di.place_street, di.tel, di.homepage, di.email, di.create_date as display_info_create_date, di.modify_date as display_info_modify_date, ri.id as reservation_info_id, ri.reservation_name, ri.reservation_tel, ri.reservation_email, ri.reservation_date, ri.cancel_flag, ri.create_date as reservation_info_create_date, ri.modify_date as reservation_info_modify_date from display_info di join ( select * from reservation_info where reservation_email = :reservationEmail ) ri on di.id = ri.display_info_id ) x join reservation_info_price rip on rip.reservation_info_id = x.reservation_info_id join  product_price pp on rip.product_price_id = pp.id group by reservation_info_id ) rdi join product p on rdi.product_id = p.id join category c on c.id = p.category_id";

    public static final String SELECT_RESERVATION_INFO_BY_RESERVATION_EMAIL_JOIN = "select ri.id as \"reservation_info_id\", ri.reservation_name as \"reservation_name\", ri.reservation_tel as \"reservation_telephone\", ri.reservation_email as \"reservation_email\", ri.reservation_date as \"reservation_date\", ri.cancel_flag as \"cancel_yn\", ri.create_date as \"create_date\",  ri.modify_date as \"modify_date\", di.id as \"display_info_id\", di.product_id as \"product_id\", sum(pp.price * rip.count) as \"total_price\", di.id as \"display_info.display_info_id\", di.product_id as \"display_info.product_id\", di.opening_hours as \"display_info.opening_hours\", di.place_name as \"display_info.place_name\", di.place_lot as \"display_info.place_lot\", di.place_street as \"display_info.place_street\", di.tel as \"display_info.telephone\", di.homepage as \"display_info.homepage\", di.email as \"display_info.email\", di.create_date as \"display_info.create_date\", di.modify_date as \"display_info.modify_date\", p.content as \"display_info.product_content\", p.description as \"display_info.product_description\", p.event as \"display_info.product_event\", p.category_id as \"display_info.category_id\", c.name as \"display_info.category_name\" from display_info di join ( select * from reservation_info where reservation_email = :reservationEmail ) ri on di.id = ri.display_info_id join product p on di.product_id = p.id join category c on c.id = p.category_id join reservation_info_price rip on rip.reservation_info_id = ri.id join  product_price pp on rip.product_price_id = pp.id group by ri.id";
}