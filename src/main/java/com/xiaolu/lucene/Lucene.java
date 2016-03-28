package com.xiaolu.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class Lucene {
	@Test
	public void createIndex(){
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			Directory dir = FSDirectory.open(new File("d://index"));
			IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			IndexWriter writer = new IndexWriter(dir, writerConfig);
			
			Document doc = new Document();
			String text = "This is the text to be indexed.";
			doc.add(new Field("fieldName", text, TextField.TYPE_STORED));
			writer.addDocument(doc);
			writer.close();
			
			DirectoryReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			QueryParser parse = new QueryParser(Version.LUCENE_40, "fieldName", analyzer);
			Query query = parse.parse("text");
			
			ScoreDoc[] hits = searcher.search(query, 100).scoreDocs;
			for(int i = 0; i < hits.length; i++){
				Document result = searcher.doc(hits[i].doc);
				System.out.println(result.get("fieldName"));
				
			}
			dir.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
