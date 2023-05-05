<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.user.*"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>

<head>
<!-- Basic -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- Mobile Metas -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<!-- Site Metas -->
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="author" content="" />

<title>Medion</title>

<!-- slider stylesheet -->
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.1.3/assets/owl.carousel.min.css" />

<!-- font awesome style -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<!-- bootstrap core css -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />

<!-- fonts style -->
<link
	href="https://fonts.googleapis.com/css?family=Poppins:400,600,700|Roboto:400,700&display=swap"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/style.css" rel="stylesheet" />
<!-- responsive style -->
<link href="css/responsive.css" rel="stylesheet" />
</head>

<body>
<%
	if(request.getParameter("succ")!=null)
	{
	out.println("<script>alert('Congratulations....Login Successfull...!')</script>");	
	}
	%>
	<div class="hero_area">
		<!-- header section strats -->
		<header class="header_section">
			<div class="container">
				<div class="top_contact-container">
					<div class="tel_container"></div>
					<div class="social-container">
						<a href=""> </a> <a href=""> </a> <a href=""> </a>
					</div>
				</div>
			</div>
			<div class="container-fluid">
				<nav class="navbar navbar-expand-lg custom_nav-container pt-3">
					<a class="navbar-brand" href="index.html"> <img
						src="images/logo.png" alt=""> <span> Medion </span>
					</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>

					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<div
							class="d-flex  flex-column flex-lg-row align-items-center w-100 justify-content-between">


							<div class="login_btn-contanier ml-0 ml-lg-5">
								<ul class="navbar-nav  ">
									<jsp:include page="Distmenu.jsp"></jsp:include>
									</ul>
							</div>
						</div>
					</div>

				</nav>
			</div>
		</header>
		<!-- end header section -->
		<!-- slider section -->
		<section class=" slider_section position-relative">
			<div id="carouselExampleIndicators" class="carousel slide"
				data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carouselExampleIndicators" data-slide-to="0"
						class="active"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<div class="container">
							<div class="row">
								<div class="col-md-4">
									<div class="img-box">
										<img src="images/medicine.png" alt="">
									</div>
								</div>
								<div class="col-md-8">
									<div class="detail-box">
										<h1>
											Welcome To Distributor  <br> <span> Online Medicine </span>

										</h1>
										<%
			String id = request.getParameter("medicinno");
	
			String oldqty="",MedicineName = "", CompanyName = "", DescriptionName = "", MFGDate = "", Price = "", ExpiryDate = "", Medicine_No = "";
			
			try {
				Connection con = (Connection) DBconn.conn();
				Statement stRegister1 = (Statement) con.createStatement();
				ResultSet rsLogin1 = stRegister1
						.executeQuery("select * from medicine_info where Medicine_No='"+id+"'");
				if (rsLogin1.next()) {
					MedicineName = rsLogin1.getString("MedicineName");
					CompanyName = rsLogin1.getString("CompanyName");
					DescriptionName = rsLogin1.getString("DescriptionName");
					MFGDate = rsLogin1.getString("MFGDate");
					Price = rsLogin1.getString("Price");
					ExpiryDate = rsLogin1.getString("ExpiryDate");
					Medicine_No = rsLogin1.getString("Medicine_No");
					
				}
				
				Statement st1 = (Statement) con.createStatement();
				ResultSet rs1 = st1
						.executeQuery("select * from medicine_info_order where Medicine_No='"+id+"'order by D_Id desc");
				if (rs1.next()) {
					oldqty = rs1.getString("Order_Qty");
				}
			}
			catch(Exception e)
			{}
			int dbprice=Integer.valueOf(Price);
			int qty=Integer.valueOf(oldqty);
			int totalprice=dbprice*qty;
			%>
			
			
			<form action="savemedicine" method="post" class="form-block form-bold form-mb-20 form-h-35 form-brdr-b-grey pr-50 pr-sm-0">
			            	<table align="center" border="1"  cellpadding="5" cellspacing="5">
					 <tr>
						<td><b>Medicine Name</b></td>
						<td><input type="text" name="MedicineName" readonly="readonly" value="<%=MedicineName %>" placeholder="Medicine Name"/></td>
						<td><b>Company Name</b></td>
						<td><input type="text" name="companyname"  readonly="readonly"  value="<%=CompanyName %>" placeholder="Company Name" title="Minimum Two  Characters  are Required For Company Name" required /></td>
					</tr>
					<tr>
						<td><b>Description</b></td>
						<td><input type="text" name="Descriptionname"  value="<%=DescriptionName %>" readonly="readonly" required /></td>
						<td><b>MFG. Date</b></td>
						<td><input type="text" name="mfgdate"  value="<%=MFGDate %>" readonly="readonly"  required="required"/>
						</td>
						</tr>
					<tr>
						<td><b>Price</b></td>
						<td><input type="text" name="price" value="<%=Price %>" readonly="readonly" required="required"/></td>
						<td><b>Expiry Date</b></td>
						<td><input type="text" name="Expirydate"  value="<%=ExpiryDate %>"  required="required" readonly="readonly"  />
						</td>
					</tr>
					<tr>
						<td><b>QTY</b></td>
						<td><input type="text" name="qty"  value="<%=oldqty %>" placeholder="Enter Qty" required="required" pattern="^[1-9]\d*(\.\d+)?$" /></td>
						
						<td><b>Total Price</b></td>
						<td><input type="text" name="totalprice"  value="<%=totalprice %>" placeholder="Enter Qty" required="required" pattern="^[1-9]\d*(\.\d+)?$" /></td>
						
						
					</tr>
					<tr>
						
						<td><input type="hidden" name="meid" readonly="readonly" value="<%=id %>"  /></td>
						
						
					</tr>
					<tr>
				     	<td colspan="2" align="center"><input type="submit" value="Pay Bill" style="background: yellow;color: black;" class="button"/></td>
				    	<td colspan="2" align="center"><input type="reset" value="Reset" style="background: yellow;color: black;" class="button"/></td>
				    </tr>
				</table>
                       
			</form>	
										</div>
								</div>
							</div>
						</div>
					</div>
					
					
				</div>

				
			</div>


		</section>
		<!-- end slider section -->
	</div>

	

	<!-- end feature section -->

	<!-- discount section -->

	

	<!-- end health section -->

	<!-- about section -->
	
	<!-- end client section -->

	<!-- contact section -->
	


	<!-- end info section -->

	<!-- footer section -->
	<section class="container-fluid footer_section">
		<p>
			&copy; 2019 All Rights Reserved. Design by <a
				href="https://html.design/">Free Html Templates</a>
		</p>
	</section>
	<!-- footer section -->

	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.2.1/owl.carousel.min.js">
		
	</script>
	<script type="text/javascript">
		$(".owl-carousel").owlCarousel({
			loop : true,
			margin : 10,
			nav : true,
			navText : [],
			autoplay : true,
			responsive : {
				0 : {
					items : 1
				},
				600 : {
					items : 2
				},
				1000 : {
					items : 4
				}
			}
		});
	</script>
	<script type="text/javascript">
		$(".owl-2").owlCarousel({
			loop : true,
			margin : 10,
			nav : true,
			navText : [],
			autoplay : true,

			responsive : {
				0 : {
					items : 1
				},
				600 : {
					items : 2
				},
				1000 : {
					items : 4
				}
			}
		});
	</script>
</body>

</html>