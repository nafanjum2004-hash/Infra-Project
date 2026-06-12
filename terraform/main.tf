data "aws_ami" "amazon_linux" {
  most_recent = true

  owners = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-*-x86_64"]
  }
}





module "security_group" {
  source = "./modules/security-group"

  security_group_name = var.security_group_name
  allowed_ports       = var.allowed_ports
}



module "ec2" {
  source = "./modules/ec2"

  instance_name    = var.instance_name
  instance_type    = var.instance_type
  ami_id           = data.aws_ami.amazon_linux.id
  security_group_id = module.security_group.security_group_id
  key_name = var.key_name
}