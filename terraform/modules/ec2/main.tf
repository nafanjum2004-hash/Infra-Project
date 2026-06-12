resource "aws_instance" "this" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  vpc_security_group_ids = [var.security_group_id]
  key_name = var.key_name


    root_block_device {
    volume_size = 10
    volume_type = "gp3"
  }

  tags = {
    Name = var.instance_name
  }
}