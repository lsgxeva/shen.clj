
"                                                   The License
 
 The user is free to produce commercial applications with the software, to distribute these applications in source or binary  form, and to charge monies for them as he sees fit and in concordance with the laws of the land subject to the following  license.
 
 1. The license applies to all the software and all derived software and must appear on such.
 2. It is illegal to distribute the software without this license attached to it and use of the software implies agreement 
    with the license as such. It is illegal for anyone who is not the copyright holder to tamper with or change the license.
 3. Neither the names of Lambda Associates or the copyright holder may be used to endorse or promote products built using
     the software without specific prior written permission from the copyright holder.
 4. That possession of this license does not confer on the copyright holder any special contractual obligation towards the    user. That in no event shall the copyright holder be liable for any direct, indirect, incidental, special, exemplary or   consequential damages (including but not limited to procurement of substitute goods or services, loss of use, data, or    profits; or business interruption), however caused and on any theory of liability, whether in contract, strict liability   or tort (including negligence) arising in any way out of the use of the software, even if advised of the possibility of   such damage. 
5. It is permitted for the user to change the software, for the purpose of improving performance, correcting an error, or    porting to a new platform, and distribute the modified version of Shen (hereafter the modified version) provided the     resulting program conforms in all respects to the Shen standard and is issued under that title. The user must it clear   with his distribution that he/she is the author of the changes and what these changes are and why. 
6. Derived versions of this software in whatever form are subject to the same restrictions. In particular it is not          permitted to make derived copies of this software which do not conform to the Shen standard or appear under a different title.
7. It is permitted to distribute versions of Shen which incorporate libraries, graphics or other facilities which are not    part of the Shen standard.

For an explication of this license see http://www.lambdassociates.org/News/june11/license.htm which explains this license in full."

(package shen- []

(define sys-print
  "" -> ""
  (@s "_quote" Str) -> (@s "'" (sys-print Str))
  (@s "_backquote" Str) -> (@s "`" (sys-print Str))
  (@s "_hash" Str) -> (@s "#" (sys-print Str))
  (@s "|:|" Str) -> (@s ":" (sys-print Str))
  (@s "|;|" Str) -> (@s ";" (sys-print Str))
  (@s "|,|" Str) -> (@s "," (sys-print Str))
  (@s S Str) -> (@s S (sys-print Str)) 

(define sys-print
  X -> X))