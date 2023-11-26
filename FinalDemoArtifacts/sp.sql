--
-- Dumping routines for database 'cs3230f23c'
--
/*!50003 DROP PROCEDURE IF EXISTS `AddRental` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddRental`(
IN p_member_id CHAR(10),
    IN p_employee_num CHAR(10),
    IN p_start_date DATE,
    IN p_end_date DATE,
    IN p_furniture_id CHAR(10),
    IN p_quantity INT,
    IN p_cost DECIMAL(6, 2)
    )
BEGIN
DECLARE v_new_return_id CHAR(10);

    INSERT INTO `Return`(member_id, employee_num)
    VALUES (p_member_id, p_employee_num);

    SET v_new_return_id = LAST_INSERT_ID();

    INSERT INTO Return_Item(rental_id, furniture_id, return_id, fine_amount, quantity)
    VALUES (p_rental_id, p_furniture_id, v_new_return_id, p_fine_amount, p_quantity);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddRentalWithMultipleItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddRentalWithMultipleItems`(
IN in_member_id CHAR(10), 
    IN in_employee_num CHAR(10), 
    IN in_start_date DATE,
    IN in_due_date DATE,
    IN furniture_ids TEXT,
    IN quantities TEXT,
    IN costs TEXT)
BEGIN
DECLARE v_rental_id CHAR(10);
    DECLARE cur_index INT DEFAULT 1;
    DECLARE cur_furniture_id CHAR(10);
    DECLARE cur_quantity INT;
    DECLARE cur_cost DECIMAL(6,2);
    
SELECT 
    IFNULL(MAX(rental_id), 'RNT0000000')
INTO v_rental_id FROM
    Rental;
    SET v_rental_id = CONCAT('RNT', LPAD(CAST(SUBSTRING(v_rental_id, 4) AS UNSIGNED) + 1, 7, '0'));

    INSERT INTO Rental(rental_id, member_id, employee_num, start_date, due_date)
    VALUES (v_rental_id, in_member_id, in_employee_num, in_start_date, in_due_date);

    WHILE cur_index <= LENGTH(furniture_ids) - LENGTH(REPLACE(furniture_ids, ',', '')) + 1 DO
        SET cur_furniture_id = SUBSTRING_INDEX(SUBSTRING_INDEX(furniture_ids, ',', cur_index), ',', -1);
        SET cur_quantity = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(quantities, ',', cur_index), ',', -1) AS SIGNED);
        SET cur_cost = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(costs, ',', cur_index), ',', -1) AS DECIMAL(6,2));

        INSERT INTO Rental_Item(rental_id, furniture_id, quantity, cost)
        VALUES (v_rental_id, cur_furniture_id, cur_quantity, cur_cost);

        SET cur_index = cur_index + 1;
    END WHILE;

SELECT v_rental_id AS rental_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddReturn` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddReturn`(
	IN p_return_id CHAR(10),
	IN p_member_id CHAR(10),
    IN p_employee_num CHAR(10)
    -- IN p_start_date DATE,
    -- IN p_end_date DATE,
    -- IN p_furniture_id CHAR(10),
    -- IN p_quantity INT,
    -- IN p_cost DECIMAL(6, 2)
    )
BEGIN
DECLARE v_new_return_id CHAR(10);

    INSERT INTO `Return`(return_id, member_id, employee_num)
    VALUES (p_return_id, p_member_id, p_employee_num);

    SET v_new_return_id = LAST_INSERT_ID();

    -- INSERT INTO Return_Item(rental_id, furniture_id, return_id, fine_amount, quantity)
    -- VALUES (p_rental_id, p_furniture_id, v_new_return_id, p_fine_amount, p_quantity);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ChangeEmployeeRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `ChangeEmployeeRole`(
    IN p_username VARCHAR(20),
    IN p_role VARCHAR(20)
)
BEGIN
    UPDATE `user`
    JOIN employee ON employee.username = `user`.username
    SET
		role = p_role
    WHERE
        `employee`.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FetchRentalDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `FetchRentalDetails`(IN in_rental_id CHAR(10))
BEGIN
SELECT 
        r.rental_id AS 'Rental ID',
        m.member_id AS 'Member ID',
        CONCAT(pi.f_name, " ", pi.l_name) AS 'Member_Fullname',
        e.employee_num AS 'Employee ID',
        CONCAT(epi.f_name, " ", epi.l_name) AS 'Employee_Fullname',
        r.start_date AS 'Start Date',
        r.due_date AS 'Due Date',
        ri.furniture_id AS 'Furniture ID',
        f.style_name AS 'Style Name',
        f.category_name AS 'Category Name',
        ri.quantity AS 'Quantity',
        ri.cost AS 'Cost Per Item',
        (ri.cost * ri.quantity) AS 'Total Cost for Item'
    FROM 
        Rental r
    JOIN Rental_Item ri ON r.rental_id = ri.rental_id
    JOIN Furniture f ON ri.furniture_id = f.furniture_id
    JOIN Member m ON r.member_id = m.member_id
    JOIN personal_information pi ON m.pid = pi.pid
    JOIN Employee e ON r.employee_num = e.employee_num
    JOIN personal_information epi ON e.pid = epi.pid
    WHERE 
        r.rental_id = in_rental_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeRentalCountAndAmount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeRentalCountAndAmount`(IN p_username VARCHAR(45))
BEGIN
    SELECT 
        COUNT(DISTINCT rental.rental_id) AS RentalCount,
        IFNULL(SUM(rental_item.cost * rental_item.quantity), 0) AS TotalAmount
    FROM
        rental
    INNER JOIN
        employee ON rental.employee_num = employee.employee_num
    LEFT JOIN
        rental_item ON rental.rental_id = rental_item.rental_id
    WHERE
        employee.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeReturnCountByUsername` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeReturnCountByUsername`(IN p_username VARCHAR(45))
BEGIN
    SELECT
        employee.employee_num,
        CONCAT(personal_information.f_name, ' ', personal_information.l_name) AS EmployeeName,
        COUNT(`return`.return_id) AS ReturnCount
    FROM
        employee
    INNER JOIN
        `return` ON employee.employee_num = `return`.employee_num
    INNER JOIN
        personal_information ON employee.pid = personal_information.pid
    WHERE
        employee.username = p_username
    GROUP BY
        employee.employee_num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeTotalFines` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeTotalFines`(IN p_username VARCHAR(45))
BEGIN
    SELECT
        SUM(ri.fine_amount) AS TotalFines
    FROM
        employee e
    INNER JOIN
        `return` r ON e.employee_num = r.employee_num
    INNER JOIN
        return_item ri ON r.return_id = ri.return_id
    WHERE
        e.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetUnreturnedItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetUnreturnedItems`(in p_rental_id CHAR(10))
BEGIN
SELECT
    ri.rental_id,
    ri.furniture_id,
    ri.cost,
    ri.quantity AS rental_quantity,
    COALESCE(roi.quantity, 0) AS return_quantity,
    ri.quantity - COALESCE(roi.quantity, 0) AS quantity_difference
FROM
    rental_item ri
LEFT JOIN
    return_item roi ON ri.rental_id = roi.rental_id AND ri.furniture_id = roi.furniture_id
WHERE
    ri.rental_id = p_rental_id
    HAVING
    quantity_difference > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `UpdateEmployee`(
    IN p_employee_num VARCHAR(20),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_bDate DATE,
    IN p_gender VARCHAR(10),
    IN p_phoneNum VARCHAR(13),
    IN p_streetAdd VARCHAR(80),
    IN p_city VARCHAR(50),
    IN p_state VARCHAR(20),
    IN p_zip VARCHAR(5)
)
BEGIN
    UPDATE personal_information
    JOIN employee ON personal_information.pid = employee.pid
    SET
        f_name = p_fName,
        l_name = p_lName,
        b_date = p_bDate,
        gender = p_gender,
        phone_num = p_phoneNum,
        street_add = p_streetAdd,
        city = p_city,
        state = p_state,
        zip = p_zip
    WHERE
        `employee`.employee_num = p_employee_num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `UpdateUser`(
    IN p_memberId VARCHAR(20),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_bDate DATE,
    IN p_gender VARCHAR(10),
    IN p_phoneNum VARCHAR(13),
    IN p_streetAdd VARCHAR(80),
    IN p_city VARCHAR(50),
    IN p_state VARCHAR(20),
    IN p_zip VARCHAR(5)
)
BEGIN
    UPDATE personal_information
    JOIN member ON personal_information.pid = member.pid
    SET
        f_name = p_fName,
        l_name = p_lName,
        b_date = p_bDate,
        gender = p_gender,
        phone_num = p_phoneNum,
        street_add = p_streetAdd,
        city = p_city,
        state = p_state,
        zip = p_zip
    WHERE
        `member`.member_id = p_memberId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-26 12:16:39