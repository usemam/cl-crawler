namespace Usemam.Cl.Crawler.Domain.Model
{
    public class Ad
    {
        public string Url { get; set; }

        public string Title { get; set; }

        public string Location { get; set; }

        public string GmapsLink { get; set; }

        public decimal Price { get; set; }
    }
}