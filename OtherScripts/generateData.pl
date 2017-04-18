#!/bin/usr/perl

use strict;
use warnings;
use Data::Dumper;

my $catalogURL = "http://catalog.colostate.edu/general-catalog/courses-az/";
my $dataURL = "https://www.cs.colostate.edu/~pbivrell/courseToCategory";

my %classToCategory = ();

my @categories_all_html = split(/\n/,`wget -qO- $catalogURL`);
my @categories_mapping = split(/\n/,`wget -qO- $dataURL`);
my @categories_all = ();

foreach(@categories_mapping){
	my @temp = split(/,/,$_);
	$classToCategory{$temp[0]} = $temp[1];
	
}

foreach(@categories_all_html){
	if($_ =~ m/general-catalog\/courses-az\/\w*\//){
		$_ =~ s/<li><a href="\/general-catalog\/courses-az\///;
		$_ =~ s/\/.*//;
		push(@categories_all,$_);
	}
}

undef @categories_all_html;

my $length = scalar @categories_all;

foreach(@categories_all){
	
	my @courses_html = split(/\n/,`wget -qO- $catalogURL$_`);
	#my @courses_html = split(/\n/,`wget -qO- $catalogURL$categories_all[20]`);
	
	my $grabNext = 0;
	my $skipBlock = 0;
	
	foreach(@courses_html){
		if($_ =~ m/<div class="courseblock">/){
			$grabNext = 1;
		}
		elsif($_ eq '<p class="courseblockdesc">'){
			$grabNext = 2;

		}	
		elsif($grabNext eq 1){
			#returns 1 if text is invalid
			$skipBlock = parse_first_block($_);
			$grabNext = 0;	
		}
		elsif($grabNext eq 2){
			if($skipBlock eq 0){
				parse_second_block($_);
			}else{
				$skipBlock = 0;
			}
			$grabNext = 0;
		}
	}
}

sub parse_first_block{
	my $line = shift;
	$line =~ s/<.+?>//g;
	$line =~ s/(&#160;)+/~/g;
	$line =~ s/&amp//g;
		
	my @data = split("~",$line);

	#Returns 1 if data could not be correctly formatted
	my $res = print_formatt_first_block(@data);	

	#Print data to STDERR for Debuging
	if($res){
		print STDERR "START [";
		foreach(@data){
			print STDERR $_ . "] , ["; 
		}
		print STDERR "] END\n";
	}

	return $res;
}

sub print_formatt_first_block{
	my @data = @_;

	#Can not build Course if Course ID, Course title, 
	#or Credits don't exisit
	if(!($data[0] and $data[1])){ 
		print STDERR "COURSE ID INCORRECT\n";
		return 1; 
	}
	if(!($data[2])){ 
		print STDERR "COURSE TITLE INCORRECT\n";
		return 1;
	}
	
	#Special Case for Credits
	my $credits = 0;
	
	if($data[3] =~ m/Var/){
		$credits = -1;
		goto SKIPPED;
	}
	
	$data[3] =~ s/(Credits|Credit)://g;
	$data[3] =~ s/\s+//g;
	if(!($data[3] =~ m/^\d+$/)){
		print STDERR "CREDITS INCORRECT\n";
		return 1;	
	}

	$credits = $data[3];	

	SKIPPED:

	$credits =~ s/^\s+|\s+$//g;
	
	#Print all data because it is valid at this point
	my $courseName = $data[0] . $data[1];
	#Print category
	my $category = 13;
	if(exists $classToCategory{$courseName}){
		$category = $classToCategory{$courseName};
	}
	$category =~ s/^\s+|\s+$//g;

	print $category . "\n";
	print $data[0] . $data[1] . "\n"; #Print courseID
	print $data[2] . "\n";			  #Print course title
	print $credits . "\n";			  #Print credits
	print $data[4] . "\n";			  #Print creditLayout
	
	return 0;
}


sub parse_second_block{
	my $line = shift;
	my @data = split("<br />",$line);
	my %mapping = ();	

	foreach(@data){
		$_ =~ s/<.+?>//g;
		$_ =~ s/&#160;//g;
		my @temp = split(":",$_,2);
		$temp[0] =~ s/^\s+//;
		$temp[1] =~ s/^\s+//;
		if($temp[0] eq "Grade Modes"){
			$temp[0] = "Grade Mode";
		}elsif($temp[0] eq "Terms Offered"){
			$temp[0] = "Term Offered";
		}
		$mapping{$temp[0]}=$temp[1];
	}
	
	print_formatt_second_block(%mapping);	
	#print Dumper(\%mapping);
	
}

sub print_formatt_second_block{
	my (%hash) = @_;
	#print Dumper(\%hash);
	
	(exists $hash{"Course Description"}) ? print $hash{"Course Description"} . "\n" : print "\n";
	(exists $hash{"Grade Mode"}) ? print $hash{"Grade Mode"} . "\n" : print "\n";
	(exists $hash{"Prerequisite"}) ? print $hash{"Prerequisite"} . "\n" : print "\n";
	(exists $hash{"Registration Information"}) ? print $hash{"Registration Information"} . "\n" : print "\n";
	(exists $hash{"Restriction"}) ? print $hash{"Restriction"} . "\n" : print "\n";
	(exists $hash{"Also Offered As"}) ? print $hash{"Also Offered As"} . "\n" : print "\n";
	(exists $hash{"Special Course Fee"}) ? print $hash{"Special Course Fee"} . "\n" : print "\n";
	(exists $hash{"Term Offered"}) ? print $hash{"Term Offered"} . "\n" : print "\n";
	
	#print (exists $hash{"Grade Mode"}) ?  $hash{"Grade Mode"} . "\n" : "";
	#print (exists $hash{"Prerequisite"}) ?  $hash{"Prerequisite"} . "\n" : "";
	#print (exists $hash{"Registration Information"}) ?  $hash{"Registration Information"} . "\n" : "";
	#print (exists $hash{"Restriction"}) ?  $hash{"Restriction"} . "\n" : "";
	#print (exists $hash{"Also Offered As"}) ?  $hash{"Also Offered As"} . "\n" : "";
	#print (exists $hash{"Special Course Fee"}) ?  $hash{"Special Course Fee"} . "\n" : "";
	#print (exists $hash{"Term Offered"}) ?  $hash{"Term Offered"} . "\n" : "";
	
	return 0;
	#print $hash{"Additional Info"}
}
