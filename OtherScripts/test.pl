#!/bin/usr/perl

@test = split('\n',`wget -q -O - http://www.cs.colostate.edu/~pbivrell/courseData`);

my $continue = 1;

my $DEBUG = 0;

#for(my $i = 7; $i < $#test; $i+=13){
for(my $i = 7; $i < $#test and $continue == 1; $i+=13){
	if($DEBUG) { $continue = 0; }
	my $curr = $test[$i];
	
	$curr =~ s/(and)/\&/g;
	$curr =~ s/(or)/\|/g;
	$curr =~ s/(concurrently)/\*/g;

	my $res = "";
	if($DEBUG){ print "Line: " . $curr . "\n";}
	
	@characters = split //,$curr;

	for(my $i = 0; $i < $#characters; $i++){
		my $c = $characters[$i];
		if($DEBUG){ print "   char: " . $c . "\n";}
		if($c eq "("){
			$res .= "(";
		}elsif($c eq ")"){
			$res .= ")";
		}elsif($c eq "&"){
			$res .= "&";
		}elsif($c eq "|"){
			$res .= "|";
		}elsif($c eq "*"){
			$res .= "*";
		}else{
			my $course = "";
			while($i < $#characters and $c =~ m/([A-Z]|\d)/){	
				$course .= $c;
				$c = $characters[++$i];
				if(!($c =~ m/([A-Z]|\d)/)){
					$i--;
					break;
				}
				if($DEBUG){ print "   char in Course: " . $c . "\n"; }
				#my $c = $characters[$i];	
				#$course .= $c;
				#print "       is part of a course: " . $course . "\n";
			}
			if($course =~ m/[A-Z]{1,6}\d{3}/){
				$res .= $course;
			}
		}
	}	
	print "$res\n";

	if($DEBUG){
		 print "Continue ";
		my $in = <STDIN>;
		chomp $in;
		$continue = $in;
	}
}

