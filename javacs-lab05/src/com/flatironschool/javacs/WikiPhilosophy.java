package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */


public void checkForLink(){

}

	public static void main(String[] args) throws IOException {
		        // some example code to get you started
        //take a url, download, parse it

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.fetchWikipedia(url);

		Element firstPara = paragraphs.get(0);
		ArrayList<String> links = new ArrayList<String>();
		Boolean keepChecking = false;


		// should traverse the resulting DOM tree to find the first valid link.
		
		Iterable<Node> iter = new WikiNodeIterable(firstPara);

		links.add(url);
		while(keepChecking == true){
			int paren = 0;
			Boolean firstLink = true;
			keepChecking = false;
			for (Node node: iter) {
				if (node instanceof TextNode) {
					for(char c : node.toString().toCharArray()){
						if(c == '('){
							paren += 1;
						}
						else if(c == ')'){
							paren -= 1;
						}
					}
				}
				else if(node instanceof Element){
					currentLink:
					if(((Element)node).tagName() == "a"){ //it is a link
						if(links.contains(node.attr("href")) && firstLink == true){ //if the firstlink we check from the para is one we've already visited
							System.out.println("FAILURE");
							return;
						}
						firstLink = false; //since we found a link the next one is no longer first.

						if(paren == 0){ //link is not in paren
							Node current = node;
							while(current.parent() != null){
								//if italics, go back to ifnd another link
								current = current.parent();
								if(((Element)current).tagName() == "i"){
									break currentLink; //break out of this link, check for next one
								}
							}
							if(url == node.attr("href")){//if our current page link = text of the link
								break currentLink;
							}

							if(node.attr("href") == "https://en.wikipedia.org/wiki/Philosophy"){ //success, we got to the philosophy page
								System.out.println("Success");
								return;
							}
								links.add(node.attr("href"));
								url = node.attr("href");
								paragraphs = wf.fetchWikipedia(url);
								firstPara = paragraphs.get(0);
								iter = new WikiNodeIterable(firstPara);
								paren = 0; //when this = 0, link is not in paren. 
								firstLink = true;
								keepChecking = true;
							
						}
						else{
							break currentLink; //break out of this link if it's in paren
						}

					}
					//if the first link is a page we have already seen, the program should indicate failure and exit.
					//If the link matches the URL of the Wikipedia page on philosophy
					//by following parent links up the tree. If there is an i or em tag in the parent chain, the link is in italics.
					//tag name for italics is i
					//for parenthesis....( = 1, )= -1...make sure by end of string it adds up to 0
					//to end program, just return. print out error.
					//getTextContext
					//skip links to current page
				}
	        }
	        if(firstLink == true){ //if we reach here and firstLink is still True, that means there have been no links we checked.
	        	System.out.println("FAILURE");
	        	return;
	        }
		}
/*
The program should build a List of the URLs it visits
 and display the results at the end (whether it succeeds or fails).
*/

        /*
        The link should be in the content text of the page, not in a sidebar or boxout.
		It should not be in italics or in parentheses.
		->has an <I> tag
		You should skip external links, links to the current page, and red links.
        */

        // the following throws an exception so the test fails
        // until you update the code
	}
}
