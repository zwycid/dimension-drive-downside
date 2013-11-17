using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;

namespace DDDMapEditorPrototype
{
    public partial class Form1 : Form
    {
        private enum ToolType
        {
            Selector,
            Start,
            Goal,
            Obstacle,
            InvisibleObstacle,
            Attractor,
            Tracer
        }

        class Obstacle
        {
            public bool visible;
            public Rectangle bound;

            public Obstacle(Point start, Point end, bool visible)
            {
                this.bound = makeRectangle(start, end);
                this.visible = visible;
            }
        }

        class DDDUnit
        {
            public Point pos;

            public DDDUnit(Point pos)
            {
                this.pos = pos;
            }
        }

        class StartPortal : DDDUnit
        {
            public StartPortal(Point pos)
                : base(pos)
            {
            }
        }

        class GoalPortal : DDDUnit
        {
            public GoalPortal(Point pos)
                : base(pos)
            {
            }
        }

        class Attractor : DDDUnit
        {
            public Attractor(Point pos)
                : base(pos)
            {
            }
        }

        class Tracer : DDDUnit
        {
            public Tracer(Point pos)
                : base(pos)
            {
            }
        }

        private ToolType selectedTool = ToolType.Selector;
        private bool dragging = false;
        private Point startPoint;

        private Bitmap previewLayer;
        private List<Obstacle> objects = new List<Obstacle>();
        private List<DDDUnit> units = new List<DDDUnit>();
        private DDDUnit startPortal;
        private DDDUnit goalPortal;

        private ImageAttributes overlayOpacity;
        private Brush brOb = new SolidBrush(ColorFromArgb(0xb2ff5858));
        private Pen penOb = new Pen(ColorFromArgb(0xb2ff5858), 3);
        private Brush brObOverlay = new SolidBrush(ColorFromArgb(0x4cff5858));
        private Pen penObOverlay = new Pen(ColorFromArgb(0x4cff5858), 3);

        public Form1()
        {
            InitializeComponent();
            previewLayer = new Bitmap(stageView.Width, stageView.Height);

            ColorMatrix cm = new ColorMatrix();
            overlayOpacity = new ImageAttributes();
            cm.Matrix33 = 0.5f;
            overlayOpacity.SetColorMatrix(cm);
        }

        private void toolSelector_Click(object sender, EventArgs e)
        {
            FieldInfo info = typeof(ToolType).GetField((sender as RadioButton).Tag.ToString());
            selectedTool = (ToolType)info.GetValue(null);
        }

        private void stageView_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left) {
                dragging = true;
                startPoint = e.Location;
                onDragBegin(startPoint);
                onDragging(startPoint, startPoint);
            }
        }

        private void stageView_MouseUp(object sender, MouseEventArgs e)
        {
            if (dragging && e.Button == MouseButtons.Left)
            {
                dragging = false;
                onDragEnd(startPoint, e.Location);
            }
        }

        private void stageView_MouseMove(object sender, MouseEventArgs e)
        {
            if (dragging) {
                onDragging(startPoint, e.Location);
            }

            coordLabel.Text = e.Location.ToString();
        }

        private void stageView_Paint(object sender, PaintEventArgs e)
        {
            drawView(e.Graphics);
        }


        public static Color ColorFromArgb(uint value)
        {
            return Color.FromArgb(unchecked((int)value));
        }

        public static Rectangle makeRectangle(Point start, Point end)
        {
            int left = Math.Min(start.X, end.X);
            int top = Math.Min(start.Y, end.Y);
            int right = Math.Max(start.X, end.X);
            int bottom = Math.Max(start.Y, end.Y);
            return new Rectangle(left, top, right - left, bottom - top);
        }

        public static Point makeImageCenteredPoint(Image image, Point pt)
        {
            return new Point(pt.X - image.Width / 2, pt.Y - image.Height / 2);
        }

        public static Rectangle makeImageCenteredRect(Image image, Point pt)
        {
            return new Rectangle(pt.X - image.Width / 2, pt.Y - image.Height / 2,
                image.Width, image.Height);
        }

        private void onDragBegin(Point start)
        {
            switch (selectedTool)
            {
                case ToolType.Start:
                    startPortal = null;
                    break;

                case ToolType.Goal:
                    goalPortal = null;
                    break;
            }
        }

        private void onDragging(Point start, Point end)
        {
            using (Graphics g = Graphics.FromImage(previewLayer))
            {
                g.Clear(ColorFromArgb(0x00ffffff));
                drawPreviewLayer(g, start, end);
            }
            stageView.Refresh();
        }

        private void onDragEnd(Point start, Point end)
        {
            switch (selectedTool)
            {
                case ToolType.Selector:
                    break;

                case ToolType.Start:
                    startPortal = new StartPortal(end);
                    break;

                case ToolType.Goal:
                    goalPortal = new GoalPortal(end);
                    break;

                case ToolType.Obstacle:
                    objects.Add(new Obstacle(start, end, true));
                    break;

                case ToolType.InvisibleObstacle:
                    objects.Add(new Obstacle(start, end, false));
                    break;

                case ToolType.Attractor:
                    units.Add(new Attractor(end));
                    break;

                case ToolType.Tracer:
                    units.Add(new Tracer(end));
                    break;
            }

            using (Graphics g = Graphics.FromImage(previewLayer))
            {
                g.Clear(ColorFromArgb(0x00ffffff));
            }
            stageView.Refresh();
        }

        private void drawPreviewLayer(Graphics g, Point start, Point end)
        {
            Image im;
            switch (selectedTool)
            {
                case ToolType.Selector:
                    break;

                case ToolType.Start:
                    im = Properties.Resources.start;
                    g.DrawImage(im, makeImageCenteredRect(im, end),
                        0, 0, im.Width, im.Height, GraphicsUnit.Pixel, overlayOpacity);
                    break;

                case ToolType.Goal:
                    im = Properties.Resources.goal;
                    g.DrawImage(im, makeImageCenteredRect(im, end),
                        0, 0, im.Width, im.Height, GraphicsUnit.Pixel, overlayOpacity);
                    break;

                case ToolType.Obstacle:
                    g.FillRectangle(brObOverlay, makeRectangle(start, end));
                    break;

                case ToolType.InvisibleObstacle:
                    g.DrawRectangle(penObOverlay, makeRectangle(start, end));
                    break;

                case ToolType.Attractor:
                    im = Properties.Resources.attractor;
                    g.DrawImage(im, makeImageCenteredRect(im, end),
                        0, 0, im.Width, im.Height, GraphicsUnit.Pixel, overlayOpacity);
                    break;

                case ToolType.Tracer:
                    im = Properties.Resources.tracer;
                    g.DrawImage(im, makeImageCenteredRect(im, end),
                        0, 0, im.Width, im.Height, GraphicsUnit.Pixel, overlayOpacity);
                    break;
            }
        }

        private void drawView(Graphics g)
        {
            g.Clear(Color.Black);
            g.SmoothingMode = SmoothingMode.AntiAlias;

            // 장애물 그리기
            foreach (Obstacle ob in objects)
            {
                if (ob.visible)
                {
                    g.FillRectangle(brOb, ob.bound);
                }
                else
                {
                    g.DrawRectangle(penOb, ob.bound);
                }
            }

            // 포탈 그리기
            if (startPortal != null)
            {
                Bitmap start = Properties.Resources.start;
                g.DrawImage(start, makeImageCenteredPoint(start, startPortal.pos));
            }

            if (goalPortal != null)
            {
                Bitmap goal = Properties.Resources.goal;
                g.DrawImage(goal, makeImageCenteredPoint(goal, goalPortal.pos));
            }

            // 유닛 그리기
            foreach (DDDUnit unit in units)
            {
                Bitmap unitImage = (
                    unit is Attractor ? Properties.Resources.attractor
                    : unit is Tracer ? Properties.Resources.tracer
                    : null);
                if (unitImage != null)
                {
                    g.DrawImage(unitImage, makeImageCenteredPoint(unitImage, unit.pos));
                }
            }


            // 드래그 중일 때 미리보기 레이어 그리기
            if (dragging)
            {
                g.DrawImage(previewLayer, 0, 0);
            }
        }

    }

}
