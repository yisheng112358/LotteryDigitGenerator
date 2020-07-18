create table membership(
	memberid int not null primary key identity(1, 1),
	useremail nvarchar(50) not null unique,
	userpwd nvarchar(50) not null,	
	isverified bit not null default(0),
);

Create Procedure checkAccount(@useremail varchar(50), @userpwd varchar(50) OUT)
AS
Begin
  Select @userpwd=userpwd From membership Where useremail = @useremail
End

